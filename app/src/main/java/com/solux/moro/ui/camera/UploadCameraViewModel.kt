package com.solux.moro.ui.camera

import android.net.Uri // import 확인
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class UploadCameraViewModel : ViewModel() {

    var showConfirmDialog by mutableStateOf(false)
    var capturedUri by mutableStateOf<Uri?>(null)

    fun onPhotoCaptured(uri: Uri) {
        capturedUri = uri
        showConfirmDialog = true
    }

    fun onConfirm() {
        showConfirmDialog = false
    }

    fun onRetry() {
        showConfirmDialog = false
        capturedUri = null
    }
}