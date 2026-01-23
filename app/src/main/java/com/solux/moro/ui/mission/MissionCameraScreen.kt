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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.solux.moro.ui.camera.CameraLayout
import com.solux.moro.ui.camera.UploadCameraViewModel
import java.io.File

@Composable
fun MissionCameraScreen(
    viewModel: UploadCameraViewModel = hiltViewModel(),
    onMissionComplete: (Uri) -> Unit = {}
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

        onCameraClick = {
            takeMissionPhoto(context, imageCapture) { uri ->
                viewModel.capturedUri = uri

                if (shotsLeft == 1) {
                    onMissionComplete(uri)
                } else {
                    viewModel.showConfirmDialog = true
                }
            }
        },


        onConfirm = {
            viewModel.capturedUri?.let { uri ->
                viewModel.onConfirm()
                onMissionComplete(uri)
            }
        },


        onRetry = {
            viewModel.onRetry()
            if (shotsLeft > 1) {
                shotsLeft--
            }
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