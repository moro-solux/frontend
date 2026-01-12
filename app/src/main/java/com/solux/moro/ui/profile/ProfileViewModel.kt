package com.solux.moro.ui.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.data.repository.UserRepository
import com.solux.moro.ui.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed class ProfileAction {
    object EditProfile : ProfileAction()
    object Follow : ProfileAction()
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository, //내 id
    val savedStateHandle: SavedStateHandle //프로필 id
) : ViewModel() {

    val user = userRepository.user
    val stats = userRepository.userStats

    val nickname =
        user.map { it?.nickname.orEmpty() }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                "@colorhunter"
            )

    val userColorHex=
        user.map { it?.userColorHex }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                "#3366FF"
            )

    val colorsCount=//   컬러맵 해금 사이즈 필요
        user.map{it?.colorPalette?.paletteColors?.size?:0}
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                0
            )
    val followerCount =
        stats.map { it?.followerCount ?: 0 }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                0
            )
    val isFollowing =
        stats.map { it?.isFollowing ?: false }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                0
            )
    val followingCount =
        stats.map { it?.followingCount ?: 0 }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                0
            )

    private val profileUserId: String =
        savedStateHandle["userId"] ?: "1"
    val myUserId: String = authRepository.myUserId()

    val isMyProfile: StateFlow<Boolean> =
        flowOf(profileUserId == myUserId)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                false
            )

    val profileAction: StateFlow<ProfileAction> =
    isMyProfile
        .map { isMine ->
            if (isMine) ProfileAction.EditProfile
            else ProfileAction.Follow
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            ProfileAction.Follow
        )

    val profileActionText: StateFlow<String> =
        profileAction
            .map { action ->
                when (action) {
                    ProfileAction.EditProfile -> "프로필 편집"
                    ProfileAction.Follow -> "팔로우"
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                "팔로우"
            )

    val userPosts = userRepository.getUserPosts(profileUserId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList() // 초기값은 빈 리스트
        )

}
