package com.solux.moro.ui.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.data.model.NotificationUiModel
import com.solux.moro.data.repository.NotificationRepository
import com.solux.moro.data.repository.UserRepository
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

    private fun loadNotifications() {
        viewModelScope.launch {
            try {

            } catch (e: Exception) {
                // 에러 처리
            }
        }
    }

    fun onNotificationClick(notificationId: Long) {
        // 서버
        viewModelScope.launch {
            notificationRepository.markAsRead(notificationId)
        }
        // 내 로컬 화면 상태
        markAsRead(notificationId)
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
}

