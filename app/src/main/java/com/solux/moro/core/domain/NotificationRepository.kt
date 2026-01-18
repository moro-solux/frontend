package com.solux.moro.core.domain

import com.solux.moro.data.model.NotificationUiModel
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun getNotifications(): Flow<Map<String, List<NotificationUiModel>>>

    suspend fun markAsRead(notificationId: Long)

    suspend fun getLikes(postId: Long): Int
}

