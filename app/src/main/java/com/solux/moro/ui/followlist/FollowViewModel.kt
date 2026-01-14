package com.solux.moro.ui.followlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.data.model.User
import com.solux.moro.data.model.UserStats
import com.solux.moro.core.domain.FollowRepository
import com.solux.moro.core.domain.UserRepository
import com.solux.moro.screens.FollowTabType
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FollowUserInfo(
    val user: User,
    val stats: UserStats
)

data class FollowUiState(
    val selectedTab: FollowTabType = FollowTabType.FOLLOWER,

    val followerSearchQuery: String = "",
    val filteredFollowers: List<FollowUserInfo> = emptyList(),

    val followingSearchQuery: String = "",
    val filteredFollowings: List<FollowUserInfo> = emptyList(),
    val isLoading: Boolean = false
)

data class FollowRequestUiState(
    val filteredFollowRequests: List<FollowUserInfo> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class FollowingViewModel @Inject constructor(
    private val followRepository: FollowRepository,
    private val userRepository: UserRepository,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var allFollowers = listOf<FollowUserInfo>()
    private var allFollowings = listOf<FollowUserInfo>()

    private val _uiState = MutableStateFlow(FollowUiState())
    val uiState: StateFlow<FollowUiState> = _uiState.asStateFlow()

    val userNickName: String? = userRepository.user.value?.nickname

    init {
        loadFollowData()
    }


    private fun loadFollowData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            allFollowers = followRepository.getFollowers()
            allFollowings = followRepository.getFollowings()

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
                else allFollowers.filter { it.user.nickname.contains(query, ignoreCase = true) }
            )
        }
    }

    // 4. following 검색
    fun updateFollowingSearch(query: String) {
        _uiState.update { state ->
            state.copy(
                followingSearchQuery = query,
                filteredFollowings = if (query.isBlank()) allFollowings
                else allFollowings.filter { it.user.nickname.contains(query, ignoreCase = true) }
            )
        }
    }

    // 언팔로우 (팔로잉 목록에서 following -> follow
    fun unFollow(userId: Long) {
        viewModelScope.launch {
            followRepository.unFollow(userId)

            allFollowings = allFollowings.filter { it.user.id != userId }
            updateFollowingSearch(_uiState.value.followingSearchQuery)
        }
    }

    // 팔로워 삭제( 팔로워 목록에서 삭제
    fun removeFollower(userId: Long) {
        viewModelScope.launch {
            followRepository.deleteFollower(userId)
            allFollowers = allFollowers.filter { it.user.id != userId }
            updateFollowerSearch(_uiState.value.followerSearchQuery)
        }
    }

}
