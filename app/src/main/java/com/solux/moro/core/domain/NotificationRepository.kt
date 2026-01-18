package com.solux.moro.core.domain

import com.solux.moro.data.dto.BaseResponse
import com.solux.moro.data.model.NotificationUiModel
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun getNotifications(): Flow<Map<String, List<NotificationUiModel>>>

    suspend fun markAsRead(notificationId: Long)

    suspend fun getLikes(postId: Long): Int

    suspend fun postToken(fcmToken: String): BaseResponse<String>

    suspend fun deleteToken(token: String): BaseResponse<String>
}

