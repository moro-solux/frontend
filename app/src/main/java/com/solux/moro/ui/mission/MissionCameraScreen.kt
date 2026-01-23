package com.solux.moro.ui.mission

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.solux.moro.ui.camera.CameraLayout
import com.solux.moro.ui.camera.UploadCameraViewModel
import java.io.File

@Composable
fun MissionCameraScreen(
    viewModel: UploadCameraViewModel = viewModel(),
    onMissionComplete: (Uri) -> Unit = {} // 최종 선택된 사진 1장만
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val previewView = remember { PreviewView(context) }
    val imageCapture = remember { ImageCapture.Builder().build() }

    // 남은 기회 (3회 시작)
    var shotsLeft by remember { mutableIntStateOf(3) }

    LaunchedEffect(Unit) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(previewView.surfaceProvider)

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageCapture
                )
            } catch (e: Exception) {
                Log.e("Camera", "카메라 연결 실패", e)
            }
        }, ContextCompat.getMainExecutor(context))
    }

    CameraLayout(
        showShotCount = true,
        remainingShots = shotsLeft, // 3 -> 2 -> 1

        showConfirmDialog = viewModel.showConfirmDialog,
        capturedImageUri = viewModel.capturedUri,

        // 1. 셔터 눌렀을 때
        onCameraClick = {
            takeMissionPhoto(context, imageCapture) { uri ->
                viewModel.capturedUri = uri

                // 마지막 기회(1)였다면? -> 바로 업로드
                if (shotsLeft == 1) {
                    onMissionComplete(uri)
                } else {
                    // 아직 기회가 남았다면(3, 2) -> 다이얼로그
                    viewModel.showConfirmDialog = true
                }
            }
        },

        // [예] 눌렀을 때
        onConfirm = {
            // 남은 기회 상관없이 바로 미션 완료
            viewModel.capturedUri?.let { uri ->
                viewModel.onConfirm() // 다이얼로그 닫기
                onMissionComplete(uri) // 화면 이동
            }
        },

        // 3. [다시 찍기] 눌렀을 때 (기회 차감)
        onRetry = {
            // 사진 버림
            viewModel.onRetry()

            // 기회 1회 차감 (3->2, 2->1)
            if (shotsLeft > 1) {
                shotsLeft--
            }
            // (참고: shotsLeft가 1일 때는 위에서 바로 통과되므로 여기로 올 일이 없음)
        },

        cameraContent = {
            AndroidView({ previewView }, Modifier.fillMaxSize())
        }
    )
}

private fun takeMissionPhoto(
    context: Context,
    imageCapture: ImageCapture,
    onSaved: (Uri) -> Unit
) {
    val file = File.createTempFile("mission_${System.currentTimeMillis()}", ".jpg", context.externalCacheDir)
    val options = ImageCapture.OutputFileOptions.Builder(file).build()

    imageCapture.takePicture(
        options, ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(res: ImageCapture.OutputFileResults) {
                val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
                onSaved(uri)
            }
            override fun onError(e: ImageCaptureException) {
                Log.e("Camera", "사진 촬영 실패", e)
            }
        }
    )
}