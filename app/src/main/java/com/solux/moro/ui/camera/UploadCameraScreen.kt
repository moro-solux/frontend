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
import java.io.File

@Composable
fun UploadCameraScreen(
    viewModel: UploadCameraViewModel = viewModel(),
    onNavigateToPost: (Uri) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    //  카메라 권한 체크
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

    // 카메라 엔진 준비
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }
    val imageCapture = remember { ImageCapture.Builder().build() } // 사진 찍는 도구

    // UI와 기능 연결
    if (hasCameraPermission) {
        CameraLayout(
            showShotCount = false,
            showConfirmDialog = viewModel.showConfirmDialog,
            capturedImageUri = viewModel.capturedUri,

            onConfirm = {
                viewModel.onConfirm()
                viewModel.capturedUri?.let { uri ->
                    onNavigateToPost(uri)
                }
            },

            onRetry = viewModel::onRetry,

            onCameraClick = {
                takePhoto(context, imageCapture) { uri ->
                    viewModel.onPhotoCaptured(uri)
                }
            },

            // 카메라 미리보기 화면 채워넣기
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
    }
}

// 사진 촬영 로직
private fun takePhoto(
    context: Context,
    imageCapture: ImageCapture,
    onPhotoCaptured: (Uri) -> Unit
) {
    // 임시 파일
    val photoFile = File.createTempFile(
        "temp_image",
        ".jpg",
        context.externalCacheDir
    )

    // CameraX에 파일 저장 설정
    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                // 파일의 주소
                val savedUri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider", // AndroidManifest의 provider와 일치해야 함
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