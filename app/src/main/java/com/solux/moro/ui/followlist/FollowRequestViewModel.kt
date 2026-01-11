package com.solux.moro.ui.followlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.data.repository.FollowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class FollowRequestViewModel @Inject constructor(
    private val followRepository: FollowRepository
) : ViewModel() {
    private var allFollowRequests = listOf<FollowUserInfo>()
    private val _uiState = MutableStateFlow(FollowRequestUiState())
    val uiState: StateFlow<FollowRequestUiState> = _uiState.asStateFlow()
    init {
        loadFollowRequestData()
    }

    private fun loadFollowRequestData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            allFollowRequests = followRepository.getFollowRequest()
            _uiState.update {
                it.copy(
                    filteredFollowRequests = allFollowRequests,
                    isLoading = false
                )
            }
        }
    }
// 팔로우 요청 수락
    fun acceptRequest(userId: Long) {
        viewModelScope.launch {
            followRepository.acceptFollowRequest(userId)
            // 수락 성공
            removeUserFromList(userId)

        }
    }

    // 팔로우 요청 거절
    fun declineRequest(userId: Long) {
        viewModelScope.launch {
            followRepository.rejectFollowRequest(userId)
            // 거절 성공
            removeUserFromList(userId)

        }
    }

    private fun removeUserFromList(userId: Long) {
        allFollowRequests = allFollowRequests.filter { it.user.id != userId }
        _uiState.update { it.copy(filteredFollowRequests = allFollowRequests) }
    }
}