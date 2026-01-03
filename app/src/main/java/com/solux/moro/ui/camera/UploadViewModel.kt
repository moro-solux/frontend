package com.solux.moro.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// 상태 관리를 위한 데이터 클래스
data class UploadState(
    val step: Int = 0, // 0:위치확인, 1:색상선택, 2:업로드준비, 3:완료
    val capturedUri: Uri? = null,
    val detectedLocation: String = "위치 정보 분석 중...",
    val analyzedColors: List<Pair<String, String>> = emptyList(), // (Hex, 퍼센트)
    val selectedColorIndex: Int? = null, // 사용자가 선택한 대표 색상 인덱스
    val isUploading: Boolean = false
)

class UploadViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UploadState())
    val uiState = _uiState.asStateFlow()

    // 화면 진입 시 초기 분석 (자동으로 4가지 색과 위치 뜸)
    fun initAnalysis(uri: Uri) {
        if (_uiState.value.capturedUri == uri) return
        _uiState.value = _uiState.value.copy(capturedUri = uri)

        viewModelScope.launch {
            // TODO: 실제 서버/Location 로직 들어갈 곳
            delay(1000) // 분석 시간 시뮬레이션

            _uiState.value = _uiState.value.copy(
                detectedLocation = "서울시 마포구 (자동 감지)",
                analyzedColors = listOf(
                    "#3357FF" to "42%",
                    "#FF5733" to "28%",
                    "#33FF57" to "18%",
                    "#F3FF33" to "12%"
                )
            )
        }
    }

    // Step 0: 위치 변경 (바텀시트에서 선택 시)
    fun updateLocation(newLocation: String) {
        _uiState.value = _uiState.value.copy(detectedLocation = newLocation)
    }

    // Step 1: 색상 선택
    fun selectColor(index: Int) {
        _uiState.value = _uiState.value.copy(selectedColorIndex = index)
    }

    // [다음] 버튼 클릭 로직
    fun nextStep() {
        val current = _uiState.value
        when (current.step) {
            0 -> {
                // 위치 확인 완료 -> 색상 선택 단계로 이동
                _uiState.value = current.copy(step = 1)
            }
            1 -> {
                // 색상 선택 완료 -> 업로드 준비 단계로 이동
                if (current.selectedColorIndex != null) {
                    _uiState.value = current.copy(step = 2)
                }
            }
        }
    }

    // Step 2 -> 3: 업로드 수행
    fun uploadPost() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isUploading = true)
            // TODO: 실제 업로드 통신
            delay(2000)
            _uiState.value = _uiState.value.copy(isUploading = false, step = 3)
        }
    }
}