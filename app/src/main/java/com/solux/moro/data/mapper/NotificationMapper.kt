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
            createdAt = this.createdAt
        )
        NotificationType.COMMENT -> NotificationUiModel.Comment(
            userName = content?.get("userName") as? String ?: "",
            content = content?.get("commentPreview") as? String ?: "",
            createdAt = this.createdAt
        )
        NotificationType.FOLLOWING -> NotificationUiModel.Following(
            userName = content?.get("userName") as? String ?: "",
            createdAt = this.createdAt
        )
        NotificationType.COLOR_UNLOCKED -> NotificationUiModel.ColorUnlocked(
            content = content?.get("content") as? String ?: "",
            createdAt = this.createdAt
        )
        NotificationType.MISSION -> NotificationUiModel.Mission(
            content = content?.get("content") as? String ?: "",
            createdAt = this.createdAt
        )
    }
}