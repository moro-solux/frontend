package com.solux.moro.ui.notification

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.solux.moro.core.domain.FollowRepository
import com.solux.moro.core.domain.NotificationRepository
import com.solux.moro.core.domain.UserRepository
import com.solux.moro.data.dto.NotificationDto
import com.solux.moro.data.mapper.toUiModel
import com.solux.moro.data.model.NotificationUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val userRepository: UserRepository,
    private val followRepository: FollowRepository
) : ViewModel(){

    var isPublic by mutableStateOf(false)
        private set

    init {
        loadPrivacyStatus()
        Log.d("isPublic", " ${isPublic}")
    }
    private val _notifications = MutableStateFlow<Map<String, List<NotificationUiModel>>>(emptyMap())
    val notifications = _notifications.asStateFlow()
    var notificationState = mutableStateOf<Map<String, List<NotificationUiModel>>>(emptyMap())
        private set

    val user = userRepository.user
    val stats = userRepository.userStats

    fun loadPrivacyStatus() {
        viewModelScope.launch {
            isPublic = notificationRepository.getPrivacyStatus()
        }
    }

    val visible=user.value.visible
    val nickname =
        user.map { it?.nickname.orEmpty() }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                "@colorhunter"
            )

    init{
        loadNotifications()
        viewModelScope.launch {
            notificationRepository.sseEvents.collect { rawData ->

                if (rawData == "connected") return@collect
                try {
                    val dto = Gson().fromJson(rawData, NotificationDto::class.java)
                    val newUiModel = dto.toUiModel()
                    Log.d("ViewModel_SSE", "새 알림 모델: $newUiModel")
//                    _notifications.update { currentMap ->
//                        val todayKey = "오늘"
//                        val todayNotifications = currentMap[todayKey] ?: emptyList()
//                        val updatedList = listOf(newUiModel) + todayNotifications
//                        currentMap.toMutableMap().apply {
//                            this[todayKey] = updatedList
//                        }.toMap()
//                    }
                    _notifications.update { currentMap ->
                        val todayKey = "Today"
                        val todayNotifications = currentMap[todayKey] ?: emptyList()

                        val updatedList = listOf(newUiModel) + todayNotifications

                        val newMap = currentMap.toMutableMap()
                        newMap[todayKey] = updatedList
                        newMap
                    }

                    //Log.d("ViewModel_SSE", "UI 모델 업데이트 완료")
                } catch (e: Exception) {
                    Log.e("ViewModel_SSE", "파싱 또는 업데이트 실패", e)
                }
            }
        }
    }

    private fun loadNotifications() {
        viewModelScope.launch {
            try {
                val mappedData = notificationRepository.getNotifications().first()

                val updatedMap = mappedData.mapValues { (_, list) ->
                    list.map { item ->
                        if (item is NotificationUiModel.Liked) {
                            val count = notificationRepository.getLikes(item.postId)
                            item.copy(totalCount = count)
                        } else item
                    }
                }

                _notifications.value = updatedMap
               // Log.d("NotificationVM", "초기 알림 로드 완료")
            } catch (e: Exception) {
                Log.e("NotificationVM", "알림 로드 실패", e)
            }
        }
    }

    fun onNotificationClick(notificationId: Long) {
        viewModelScope.launch {
            try {
                notificationRepository.markAsRead(notificationId)
                markAsRead(notificationId)
            } catch (e: Exception) {
                Log.e("NotificationVM", "읽음 처리 실패", e)
            }
        }
    }

    fun markAsRead(notificationId: Long) {
        _notifications.update { currentMap ->
            currentMap.mapValues { entry ->
                entry.value.map { item ->
                    if (item.id == notificationId) {
                        item.markAsRead()
                    } else item
                }
            }
        }
    }

    private fun updateLikeCounts(data: Map<String, List<NotificationUiModel>>) {
        viewModelScope.launch {
            data.values.flatten().filterIsInstance<NotificationUiModel.Liked>()
                .forEach { likedItem ->
                    try {
                        val likeInfo = notificationRepository.getLikes(likedItem.postId)
                    } catch (e: Exception) {
                        Log.e("LikeUpdate", "좋아요 수 업데이트 실패", e)
                    }
                }
        }
    }

    fun registerToken(fcmToken: String, myToken: String) {
        viewModelScope.launch {
            try {
                val response = notificationRepository.postToken(fcmToken)

                if (response.success) {
                    Log.d("FCM_LOG", "서버에 토큰 등록 성공!")

                    connectToNotificationStream(myToken)
                } else {
                    Log.e("FCM_LOG", "서ver 등록 실패: ${response.message}")
                }
            } catch (e: Exception) {
                Log.e("FCM_LOG", "네트워크 에러: ${e.message}")
            }
        }
    }

    private fun connectToNotificationStream(token: String) {
        notificationRepository.connectNotificationStream(token)
    }

    fun onFollow(userId:Long,notificationId: Long) {
        viewModelScope.launch {
            val result = followRepository.followRequest(userId)
            notificationRepository.markAsRead(notificationId)
            result.onSuccess {
                result.onSuccess {
                    _notifications.update { currentMap ->
                        currentMap.mapValues { (_, list) ->
                            list.map { notification ->
                                if (notification is NotificationUiModel.Following && notification.userId == userId) {
                                    notification.copy(isFollowing = true)
                                } else {
                                    notification
                                }
                            }
                        }
                    }
                }.onFailure { error ->
                    Log.d("FollowViewModel", "팔로우 요청 실패${error.message}")
                }
            }
        }
    }
        fun unFollow(userId: Long,notificationId: Long) {
            viewModelScope.launch {
                val result = followRepository.unFollow(userId)
                notificationRepository.markAsRead(notificationId)
                result.onSuccess {
                    _notifications.update { currentMap ->
                        currentMap.mapValues { (_, list) ->
                            list.map { notification ->
                                if (notification is NotificationUiModel.Following && notification.userId == userId) {
                                    notification.copy(isFollowing = false)
                                } else {
                                    notification
                                }
                            }
                        }
                    }
                }.onFailure { error ->
                    Log.d("FollowViewModel", "언팔로우 실패${error.message}")
                }
            }
        }

}

