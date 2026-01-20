package com.solux.moro.data.mapper

import com.solux.moro.data.dto.NotificationDto
import com.solux.moro.data.model.NotificationType
import com.solux.moro.data.model.NotificationUiModel

fun NotificationDto.toUiModel(): NotificationUiModel {
    val notificationType = try {
        NotificationType.valueOf(this.type)
    } catch (e: Exception) {
        return NotificationUiModel.Mission(
            content = this.content?.get("content") as? String ?: "",
            createdAt = this.createdAt,
            isRead = this.isRead,
            id = (this.id as? Number)?.toLong() ?: -1L
        )
    }

    return when (notificationType) {
        NotificationType.LIKED -> NotificationUiModel.Liked(
            userName = content?.get("actorName") as? String ?: "",
            userId= (content?.get("actorId") as? Number)?.toLong() ?: -1L,
            imageUrl = content?.get("imageUrl") as? String?: "",
            createdAt = this.createdAt,
            isRead = this.isRead,
            postId = (content?.get("postId") as? Number)?.toLong() ?: -1L,
            id = (this.id as? Number)?.toLong() ?: -1L
        )
        NotificationType.COMMENT -> NotificationUiModel.Comment(
            userName = content?.get("actorName") as? String ?: "",
            userId= (content?.get("actorId") as? Number)?.toLong() ?: -1L,
            content = content?.get("commentPreview") as? String ?: "",
            createdAt = this.createdAt,
            isRead = this.isRead,
            postId = (content?.get("postId") as? Number)?.toLong() ?: -1L,
            id = (this.id as? Number)?.toLong() ?: -1L
        )
        NotificationType.FOLLOWING -> NotificationUiModel.Following(
            userName = content?.get("actorName") as? String ?: "",
            userId= (content?.get("actorId") as? Number)?.toLong() ?: -1L,
            createdAt = this.createdAt,
            isRead = this.isRead,
            isFollowing = content?.get("isFollowing") as? Boolean ?: false,
            id = (this.id as? Number)?.toLong() ?: -1L
        )
        NotificationType.COLOR_UNLOCKED -> NotificationUiModel.ColorUnlocked(
            createdAt = this.createdAt,
            isRead = this.isRead,
            id = (this.id as? Number)?.toLong() ?: -1L
        )
        NotificationType.MISSION -> NotificationUiModel.Mission(
            content = content?.get("content") as? String ?: "",
            createdAt = this.createdAt,
            isRead = this.isRead,
            id = (this.id as? Number)?.toLong() ?: -1L
        )
    }
}