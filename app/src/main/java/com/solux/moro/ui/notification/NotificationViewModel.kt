package com.solux.moro.ui.notification

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.core.domain.NotificationRepository
import com.solux.moro.core.domain.UserRepository
import com.solux.moro.data.model.NotificationUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val userRepository: UserRepository
) : ViewModel(){

    private val _notifications = MutableStateFlow<Map<String, List<NotificationUiModel>>>(emptyMap())
    val notifications = _notifications.asStateFlow()
    val user = userRepository.user
    val stats = userRepository.userStats

    val nickname =
        user.map { it?.nickname.orEmpty() }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                "@colorhunter"
            )

    init{
        loadNotifications()
    }

    private fun loadNotifications() {
        viewModelScope.launch {
            try {
                notificationRepository.getNotifications()
                    .collect { mappedData ->
                        Log.d("NotificationVM", "데이터 수신 성공: $mappedData")
                        _notifications.value = mappedData

                        val updatedMap = mappedData.mapValues { (_, list) ->
                            list.map { item ->
                                if (item is NotificationUiModel.Liked) {
                                    val count = notificationRepository.getLikes(item.postId)
                                    Log.d("NotificationVM", "liked 데이터 수신 성공: $count")
                                    item.copy(totalCount = count)
                                } else item
                            }
                        }
                        _notifications.value = updatedMap
                    }
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
}

