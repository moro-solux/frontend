package com.solux.moro.data.mapper

import com.solux.moro.data.dto.NotificationDto
import com.solux.moro.data.model.NotificationType
import com.solux.moro.data.model.NotificationUiModel

fun NotificationDto.toUiModel(): NotificationUiModel {
    val typeEnum = try { NotificationType.valueOf(this.type) }
    catch (e: Exception) { NotificationType.MISSION }

    return when (typeEnum) {
        NotificationType.LIKED -> NotificationUiModel.Liked(
            userName = content?.get("userName") as? String ?: "",
            imageUrl = content?.get("imageUrl") as? String?: "",
            createdAt = this.createdAt,
            isRead = this.isRead,
            postId = content?.get("postId") as? Long ?: -1,
            id = content?.get("id") as? Long ?: -1,
        )
        NotificationType.COMMENT -> NotificationUiModel.Comment(
            userName = content?.get("userName") as? String ?: "",
            content = content?.get("commentPreview") as? String ?: "",
            createdAt = this.createdAt,
            isRead = this.isRead,
            postId = content?.get("postId") as? Long ?: -1,
            id = content?.get("id") as? Long ?: -1,
        )
        NotificationType.FOLLOWING -> NotificationUiModel.Following(
            userName = content?.get("userName") as? String ?: "",
            createdAt = this.createdAt,
            isRead = this.isRead,
            id = content?.get("id") as? Long ?: -1,
        )
        NotificationType.COLOR_UNLOCKED -> NotificationUiModel.ColorUnlocked(
            content = content?.get("content") as? String ?: "",
            createdAt = this.createdAt,
            isRead = this.isRead,
            id = content?.get("id") as? Long ?: -1,
        )
        NotificationType.MISSION -> NotificationUiModel.Mission(
            content = content?.get("content") as? String ?: "",
            createdAt = this.createdAt,
            isRead = this.isRead,
            id = content?.get("id") as? Long ?: -1,
        )
    }
}