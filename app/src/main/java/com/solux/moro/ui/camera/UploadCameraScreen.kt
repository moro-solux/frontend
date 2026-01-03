package com.solux.moro.ui.camera

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
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
import com.solux.moro.ui.main.createImageUri
import java.io.File

@Composable
fun UploadCameraScreen(
    viewModel: CameraViewModel = viewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // 1. 카메라 권한 체크
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> hasCameraPermission = granted }
    )

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    // 2. 카메라 엔진 준비
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }
    val imageCapture = remember { ImageCapture.Builder().build() } // 사진 찍는 도구

    // 3. UI와 기능 연결
    if (hasCameraPermission) {
        CameraLayout(
            showShotCount = false,
            showConfirmDialog = viewModel.showConfirmDialog,
            capturedImageUri = viewModel.capturedUri, // 뷰모델의 사진 전달
            onConfirm = viewModel::onConfirm,
            onRetry = viewModel::onRetry,

            onCameraClick = {
                takePhoto(context, imageCapture) { uri ->
                    viewModel.onPhotoCaptured(uri) // 찍히면 뷰모델에 알림
                }
            },

            // cameraContent 카메라 미리보기 채워넣기
            cameraContent = {
                AndroidView(
                    factory = {
                        val preview = Preview.Builder().build()
                        val selector = CameraSelector.DEFAULT_BACK_CAMERA

                        preview.setSurfaceProvider(previewView.surfaceProvider)

                        try {
                            val cameraProvider = cameraProviderFuture.get()
                            cameraProvider.unbindAll()
                            cameraProvider.bindToLifecycle(
                                lifecycleOwner,
                                selector,
                                preview,
                                imageCapture
                            )
                        } catch (e: Exception) {
                            Log.e("Camera", "Binding failed", e)
                        }
                        previewView
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        )
    } else {
        // 권한 없을 때 (검은 화면 등 처리)
    }
}

private fun takePhoto(
    context: Context,
    imageCapture: ImageCapture,
    onPhotoCaptured: (Uri) -> Unit
) {
    // 1. 파일 만들기
    val photoFile = File.createTempFile(
        "temp_image",
        ".jpg",
        context.externalCacheDir
    )

    // 2. CameraX에 "이 파일에 저장해!" 라고 설정
    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                // 3. 사진 저장이 성공하면, 그 파일의 주소(Uri)를 만들어서 보냄
                val savedUri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",
                    photoFile
                )
                onPhotoCaptured(savedUri)
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("Camera", "사진 촬영 실패: ${exception.message}", exception)
                Toast.makeText(context, "사진 촬영 실패", Toast.LENGTH_SHORT).show()
            }
        }
    )
}