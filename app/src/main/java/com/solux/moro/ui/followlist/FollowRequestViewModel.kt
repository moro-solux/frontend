package com.solux.moro.ui.followlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.core.domain.FollowRepository
import com.solux.moro.data.model.UserInfo
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
    private var allFollowRequests = listOf<UserInfo>()
    private val _uiState = MutableStateFlow(FollowRequestUiState())
    val uiState: StateFlow<FollowRequestUiState> = _uiState.asStateFlow()

    init {
        loadFollowRequestData()
    }

    private fun loadFollowRequestData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = followRepository.getFollowRequest()
            allFollowRequests = result.getOrDefault(emptyList())
            _uiState.update {
                it.copy(
                    filteredFollowRequests = allFollowRequests,
                    isLoading = false
                )
            }
        }
    }
// 팔로우 요청 수락
    fun acceptRequest(requestId: Long) {
        viewModelScope.launch {
            val result = followRepository.acceptFollowRequest(requestId)
            result.onSuccess {
                removeUserFromList(requestId)
                Log.d("FollowRequestVM","팔로우 요청 수락")
            }.onFailure {
                Log.d("FollowRequestVM","팔로우 요청 수락 실패")
            }
        }
    }

    // 팔로우 요청 거절
    fun declineRequest(requestId: Long) {
        viewModelScope.launch {
            val result = followRepository.rejectFollowRequest(requestId)
            result.onSuccess {
                removeUserFromList(requestId)
            }.onFailure {
                Log.d("FollowRequestVM","팔로우 요청 거절 실패")
            }
        }
    }

    private fun removeUserFromList(requestId: Long) {
        allFollowRequests = allFollowRequests.filter { it.followId != requestId }
        _uiState.update { it.copy(filteredFollowRequests = allFollowRequests) }
    }
}