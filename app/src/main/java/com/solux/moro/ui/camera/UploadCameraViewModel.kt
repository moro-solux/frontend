package com.solux.moro.ui.camera

import android.net.Uri // import 확인
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class UploadCameraViewModel : ViewModel() {

    // 다이얼로그 표시 여부
    var showConfirmDialog by mutableStateOf(false)

    // 찍은 사진의 주소를 저장할 변수 추가
    var capturedUri by mutableStateOf<Uri?>(null)

    // 사진 촬영 완료 시 호출
    fun onPhotoCaptured(uri: Uri) {
        capturedUri = uri
        showConfirmDialog = true
    }

    fun onConfirm() {
        showConfirmDialog = false
        // TODO: capturedUri를 서버로 업로드하거나 다음 화면으로 넘김
    }

    fun onRetry() {
        showConfirmDialog = false
        capturedUri = null // 재촬영이니 기존 사진 초기화
    }
}