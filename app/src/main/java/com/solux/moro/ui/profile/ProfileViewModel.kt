package com.solux.moro.ui.profile

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.core.domain.FeedRepository
import com.solux.moro.core.domain.FollowRepository
import com.solux.moro.core.domain.UserRepository
import com.solux.moro.data.model.ProfileFeedItem
import com.solux.moro.data.model.UserStats
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class ProfileAction {
    object EditProfile : ProfileAction()
    object Follow : ProfileAction()
}
data class PostQuery(
    val viewType: String = "DEFAULT",
    val colorId: Int? = null
)
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val followRepository: FollowRepository,
    private val feedRepository: FeedRepository,
    val savedStateHandle: SavedStateHandle //프로필 id
) : ViewModel() {

    val user = userRepository.user
    private val _uiStats = MutableStateFlow<UserStats?>(null)
    val stats = _uiStats.asStateFlow()


    val myUserId: StateFlow<Long> = userRepository.currentUserId
    private val _selectedColorId = MutableStateFlow<Int?>(null)
    private val _viewType = MutableStateFlow("DEFAULT")
    private val argumentUserId = savedStateHandle.getStateFlow<String?>("userId", null)
    private val _postQuery = MutableStateFlow(PostQuery())
    @OptIn(ExperimentalCoroutinesApi::class)
    val currentProfileId: Flow<Long> = argumentUserId
        .flatMapLatest { argId ->
            if (argId != null) {
                flowOf(argId.toLong()) // 타인 프로필
            } else {
                userRepository.currentUserId // 내 ID
            }
        }
    fun refreshProfile() {
        viewModelScope.launch {
            val argumentId = savedStateHandle.get<String>("userId")?.toLongOrNull() ?: -1L
            userRepository.loadUser(argumentId)
            Log.d("ProfileVM", "데이터 리프레시: $argumentId")
            Log.d("ProfileVM","isFollowing :${_uiStats.value?.isFollowing}")
        }
    }
    fun onChangeViewType(newType: String) {
        _viewType.value = newType

        if (newType != "SINGLE_COLOR") {
            _selectedColorId.value = null
        }
    }

    fun onColorSelected(colorId: Int?) {
        if (colorId == null) {
            _viewType.value = "DEFAULT"
            _selectedColorId.value = null
        } else {
            _viewType.value = "SINGLE_COLOR"
            _selectedColorId.value = colorId
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val userPosts: StateFlow<List<ProfileFeedItem>> = combine(
        currentProfileId,
        _viewType,
        _selectedColorId
    ) { id, viewType, colorId ->
        Triple(id, viewType, colorId)
    }.flatMapLatest { (id, viewType, colorId) ->
        if (id == -1L) {
            flowOf(emptyList())
        } else {
            userRepository.getUserPosts(id, viewType, colorId)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())


    val isMyProfile: StateFlow<Boolean> = currentProfileId
        .map { id ->
            // 전달받은 ID가 없거나, 현재 ID가 내 ID와 같을 때
            argumentUserId.value == null || id == userRepository.currentUserId.value
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)

    init {
        viewModelScope.launch {
            userRepository.userStats.collect { newStats ->
                _uiStats.value = newStats
            }
        }

        viewModelScope.launch {
            val argumentId = savedStateHandle.get<String>("userId")?.toLong()
            val targetId = argumentId ?: -1L
            userRepository.loadUser(targetId)
        }

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

    fun onFollow(userId:Long){
        viewModelScope.launch {
            val result = followRepository.followRequest(userId)
            result.onSuccess {
                Log.d("ProfileVM","팔로우 요청")
                _uiStats.update {
                    it?.copy(isFollowing = true)
                }
            }.onFailure {
                Log.d("ProfileVM","isFollowing ${_uiStats.value?.isFollowing}")

                Log.d("ProfileVM","${user.value.id}")
                Log.d("ProfileVM","팔로우 요청 실패")
            }
        }
    }


    fun unFollow(userId:Long){
        viewModelScope.launch {
            val result = followRepository.unFollow(userId)
            result.onSuccess {
                Log.d("ProfileVM","언팔")
                _uiStats.update {
                    it?.copy(isFollowing = false)
                }
            }.onFailure {
                Log.d("ProfileVM","언팔 실패")
            }
        }
    }
}
