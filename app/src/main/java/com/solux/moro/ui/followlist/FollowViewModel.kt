package com.solux.moro.ui.followlist

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.core.domain.FollowRepository
import com.solux.moro.core.domain.UserRepository
import com.solux.moro.data.model.UserInfo
import com.solux.moro.screens.FollowTabType
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class FollowUiState(
    val selectedTab: FollowTabType = FollowTabType.FOLLOWER,

    val followerSearchQuery: String = "",
    val filteredFollowers: List<UserInfo> = emptyList(),

    val followingSearchQuery: String = "",
    val filteredFollowings: List<UserInfo> = emptyList(),
    val isLoading: Boolean = false
)

data class FollowRequestUiState(
    val filteredFollowRequests: List<UserInfo> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class FollowingViewModel @Inject constructor(
    private val followRepository: FollowRepository,
    private val userRepository: UserRepository,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var allFollowers: List<UserInfo> =emptyList()
    private var allFollowings: List<UserInfo> = emptyList()

    private val _uiState = MutableStateFlow(FollowUiState())
    val uiState: StateFlow<FollowUiState> = _uiState.asStateFlow()

    private val targetUserId: Long = savedStateHandle.get<Long>("userId") ?: 0L

    init {
        loadFollowData()
    }


    private fun loadFollowData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val followersResult = followRepository.getFollowers(targetUserId, page = 0, size = 100, keyWord = "")
            val followingsResult = followRepository.getFollowings(targetUserId)

            allFollowers = followersResult.getOrDefault(emptyList())
            allFollowings = followingsResult.getOrDefault(emptyList())

            _uiState.update {
                it.copy(
                    filteredFollowers = allFollowers,
                    filteredFollowings = allFollowings,
                    isLoading = false
                )
            }
        }
    }


    fun switchTab(tab: FollowTabType) {
        _uiState.update { it.copy(selectedTab = tab) }
    }

    // follower 검색
    fun updateFollowerSearch(query: String) {
        _uiState.update { state ->
            state.copy(
                followerSearchQuery = query,
                filteredFollowers = if (query.isBlank()) allFollowers
                else allFollowers.filter { it.userName.contains(query, ignoreCase = true) }
            )
        }
    }

    // 4. following 검색
    fun updateFollowingSearch(query: String) {
        _uiState.update { state ->
            state.copy(
                followingSearchQuery = query,
                filteredFollowings = if (query.isBlank()) allFollowings
                else allFollowings.filter { it.userName.contains(query, ignoreCase = true) }
            )
        }
    }

    // 언팔로우 (팔로잉 목록에서 following -> follow
    fun unFollow(userId: Long) {
        viewModelScope.launch {
            val result =followRepository.unFollow(userId)
            result.onSuccess {
                allFollowings = allFollowings.filter { it.userId != userId }
                updateFollowingSearch(_uiState.value.followingSearchQuery)
            }.onFailure {
                Log.d("FollowViewModel", "언팔로우 실패")
            }
        }
    }

    // 팔로워 삭제( 팔로워 목록에서 삭제
    fun removeFollower(userId: Long) {
        viewModelScope.launch {
            val result =followRepository.deleteFollower(userId)
            result.onSuccess {
                allFollowers = allFollowers.filter { it.userId != userId }

            updateFollowerSearch(_uiState.value.followerSearchQuery)
            }.onFailure {
                Log.d("FollowViewModel", "팔로워 삭제 실패")
            }
        }
    }

}
