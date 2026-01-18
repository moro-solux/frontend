package com.solux.moro.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.data.dto.response.ColorCandidateDto
import com.solux.moro.data.repository.ColorMapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EditPostUiState(
    val username: String = "",
    val imageUrl: String = "",
    val detectedLocation: String = "위치 정보 없음",
    val analyzedColors: List<ColorCandidateDto> = emptyList(),
    val colorCandidates: List<ColorCandidateDto> = emptyList(),
    val selectedColorIndex: Int? = null,
    val isUpdating: Boolean = false
)

@HiltViewModel
class EditPostViewModel @Inject constructor(
    private val repository: ColorMapRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditPostUiState())
    val uiState: StateFlow<EditPostUiState> = _uiState.asStateFlow()

    private var currentPostId: Long = -1


    fun loadPostData(colorId: Long, postId: Long) {
        currentPostId = postId
        viewModelScope.launch {
            try {
                // 상세 조회 API 호출
                val response = repository.getPostDetail(colorId, postId)

                if (response.isSuccessful) {
                    response.body()?.data?.let { data ->
                        val initialSelectedColorIndex = data.colorCandidates.indexOfFirst {
                            it.colorId == data.mainColorId
                        }

                        _uiState.value = _uiState.value.copy(
                            username = data.username,
                            imageUrl = data.imageUrl,
                            detectedLocation = "등록된 장소", // 서버 데이터에 위치 정보가 있다면 여기에 매핑
                            analyzedColors = data.colorCandidates,
                            colorCandidates = data.colorCandidates,
                            selectedColorIndex = if (initialSelectedColorIndex != -1) initialSelectedColorIndex else 0
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // 필요 시 에러 처리 로직
            }
        }
    }


    fun selectColor(index: Int) {
        _uiState.value = _uiState.value.copy(selectedColorIndex = index)
    }

    fun updatePost(onSuccess: () -> Unit) {
        val selectedIndex = _uiState.value.selectedColorIndex ?: return

        if (selectedIndex !in _uiState.value.analyzedColors.indices) return

        val newColorId = _uiState.value.analyzedColors[selectedIndex].colorId

        _uiState.value = _uiState.value.copy(isUpdating = true)

        viewModelScope.launch {
            try {
                val request = mapOf("newColorId" to newColorId)
                val response = repository.updatePostColor(currentPostId, request)

                if (response.isSuccessful) {
                    onSuccess()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _uiState.value = _uiState.value.copy(isUpdating = false)
            }
        }
    }

    fun deletePost(postId: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.deletePost(postId)

                if (response.isSuccessful) {
                    onSuccess()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}