package com.solux.moro.ui.profile

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.core.domain.FeedRepository
import com.solux.moro.core.domain.FollowRepository
import com.solux.moro.core.domain.UserRepository
import com.solux.moro.data.model.ProfileFeedItem
import com.solux.moro.ui.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
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
    private val feedRepository: FeedRepository,
    val savedStateHandle: SavedStateHandle //프로필 id
) : ViewModel() {

    val user = userRepository.user
    val stats = userRepository.userStats
    val myUserId: StateFlow<Long> = userRepository.currentUserId
    //private val profileUserId: Long = ((savedStateHandle.get<String>("userId"))?.toLong() ?: myUserId.value) as Long

    private val argumentUserId = savedStateHandle.getStateFlow<String?>("userId", null)
    @OptIn(ExperimentalCoroutinesApi::class)
    val currentProfileId: Flow<Long> = argumentUserId
        .flatMapLatest { argId ->
            if (argId != null) {
                flowOf(argId.toLong()) // 타인 프로필
            } else {
                userRepository.currentUserId // 내 ID
            }
        }
    @OptIn(ExperimentalCoroutinesApi::class)
    val userPosts: StateFlow<List<ProfileFeedItem>> = currentProfileId
        .flatMapLatest { id ->
            if (id == -1L) flowOf(emptyList())
            else userRepository.getUserPosts(id)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    // 4. 내 프로필 여부
    val isMyProfile: StateFlow<Boolean> = currentProfileId
        .map { id ->
            // 전달받은 ID가 없거나, 현재 ID가 내 ID와 같을 때
            argumentUserId.value == null || id == userRepository.currentUserId.value
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)

    init {
        viewModelScope.launch {
            val argumentId = savedStateHandle.get<String>("userId")?.toLong()
            val targetId = argumentId ?: -1L
            userRepository.loadUser(targetId)
            val finalId = if (targetId == -1L) userRepository.currentUserId.value else targetId
            Log.d("ProfileViewModel", "최종 프로필 ID: $finalId")
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

//    val isMyProfile: StateFlow<Boolean> =
//        flowOf(profileUserId == -1L)
//            .stateIn(
//                viewModelScope,
//                SharingStarted.WhileSubscribed(5_000),
//                false
//            )

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


//    val userPosts = userRepository.getUserPosts(profileUserId)
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
