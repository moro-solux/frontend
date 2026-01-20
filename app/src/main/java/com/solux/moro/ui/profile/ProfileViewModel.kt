package com.solux.moro.ui.profile

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.core.domain.FollowRepository
import com.solux.moro.core.domain.UserRepository
import com.solux.moro.ui.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class ProfileAction {
    object EditProfile : ProfileAction()
    object Follow : ProfileAction()
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository, //내 id
    private val followRepository: FollowRepository,
    val savedStateHandle: SavedStateHandle //프로필 id
) : ViewModel() {

    val user = userRepository.user
    val stats = userRepository.userStats

    private val profileUserId: Long = (savedStateHandle.get<String>("userId"))?.toLong() ?: 5L
    val myUserId: Long = authRepository.myUserId().toLong()

    init {
        viewModelScope.launch {
            userRepository.loadUser(profileUserId)
            Log.d("ProfileViewModel", "profileUserId: $profileUserId")
        }
    }

    val nickname =
        user.map { it.nickname.orEmpty() }
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
                "#B1271A"
            )

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


    val userPosts = userRepository.getUserPosts(profileUserId)
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5_000),
//            initialValue = emptyList() // 초기값 빈 리스트
//        )

    fun onFollow(userId:Long){
        viewModelScope.launch {
            val result = followRepository.followRequest(userId)
            result.onSuccess {
                Log.d("ProfileVM","팔로우 요청")
            }.onFailure {
                Log.d("ProfileVM","팔로우 요청 실패")
            }
        }
    }


    fun unFollow(userId:Long){
        viewModelScope.launch {
            val result = followRepository.unFollow(userId)
            result.onSuccess {
                Log.d("ProfileVM","언팔")
            }.onFailure {
                Log.d("ProfileVM","언팔 실패")
            }
        }
    }
}
