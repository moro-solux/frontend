package com.solux.moro.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.solux.moro.R

enum class NotificationType(

) {
    COMMENT,
    LIKED,
    FOLLOWING,
    MISSION,
    COLOR_UNLOCKED;
    @DrawableRes
    fun iconRes(): Int = when (this) {
        COMMENT -> R.drawable.img_notification_profile
        LIKED -> R.drawable.img_notification_like
        FOLLOWING -> R.drawable.img_notification_follow
        MISSION -> R.drawable.img_notification_mission
        COLOR_UNLOCKED -> R.drawable.img_notification_unlock
    }

    @StringRes
    fun messageRes(): Int = when (this) {
        COMMENT -> R.string.notification_comment_message
        LIKED    -> R.string.notification_like_message
        FOLLOWING  -> R.string.notification_follow_message
        MISSION ->  R.string.notification_mission_message
        COLOR_UNLOCKED  -> R.string.notification_unlock_message
    }

    @StringRes
    fun contentRes(): Int = when (this) {
        COMMENT -> R.string.notification_comment_content
        LIKED    -> R.string.notification_like_content
        FOLLOWING  ->R.string.notification_follow_content
        MISSION -> R.string.notification_mission_content
        COLOR_UNLOCKED  -> R.string.notification_unlock_content
    }
}



sealed class NotificationUiModel {
    abstract val id: Long
    abstract val type: NotificationType
    abstract val createdAt: String
    abstract val isRead: Boolean

    fun markAsRead(): NotificationUiModel {
        return when (this) {
            is Comment -> this.copy(isRead = true)
            is Liked -> this.copy(isRead = true)
            is Following -> this.copy(isRead = true)
            is ColorUnlocked -> this.copy(isRead = true)
            is Mission -> this.copy(isRead = true)
        }
    }
    data class Comment(
        override val id: Long,
        val userName: String,
        val postId: Long,
        val content: String,
        override val createdAt: String,
        override val type: NotificationType = NotificationType.COMMENT,
        override var isRead: Boolean = false
    ) : NotificationUiModel()

    data class Liked(
        override val id: Long,
        val userName: String,
        val postId: Long,
        val imageUrl: String?,
        override val createdAt: String,
        override val type: NotificationType = NotificationType.LIKED,
        override var isRead: Boolean = false
    ) : NotificationUiModel()

    data class Following(
        override val id: Long,
        val userName: String,
        override val createdAt: String,
        override val type: NotificationType = NotificationType.FOLLOWING,
        override var isRead: Boolean = false
    ) : NotificationUiModel()

    data class ColorUnlocked(
        override val id: Long,
        override val createdAt: String,
        override val type: NotificationType = NotificationType.COLOR_UNLOCKED,
        override var isRead: Boolean = false
    ) : NotificationUiModel()

    data class Mission(
        override val id: Long,
        val content: String,
        override val createdAt: String,
        override val type: NotificationType = NotificationType.MISSION,
        override var isRead: Boolean = false,

    ): NotificationUiModel()
}
