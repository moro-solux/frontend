package com.solux.moro.data.repository

import com.solux.moro.data.model.NotificationUiModel
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getNotifications(): Flow<Map<String, List<NotificationUiModel>>>

    fun markAsRead(notificationId: Long)

    fun onNotificationClick(notificationId: Long)
}