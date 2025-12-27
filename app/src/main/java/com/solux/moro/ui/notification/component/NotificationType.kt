package com.solux.moro.ui.notification.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.solux.moro.R

enum class NotificationType(
    val needsName: Boolean,
    val needsContent:Boolean
) {
    COMMENT(true,true),
    LIKE(true,false),
    FOLLOW(true,false),
    MISSION(false,false),
    UNLOCK(false,true);
    @DrawableRes
    fun iconRes(): Int = when (this) {
        COMMENT -> R.drawable.img_notification_profile
        LIKE    -> R.drawable.img_notification_like
        FOLLOW  -> R.drawable.img_notification_follow
        MISSION -> R.drawable.img_notification_mission
        UNLOCK -> R.drawable.img_notification_lock
    }

    @StringRes
    fun messageRes(): Int = when (this) {
        COMMENT -> R.string.notification_comment_message
        LIKE    -> R.string.notification_like_message
        FOLLOW  -> R.string.notification_follow_message
        MISSION ->  R.string.notification_mission_message
        UNLOCK  -> R.string.notification_unlock_message
    }

    @StringRes
    fun contentRes(): Int = when (this) {
        COMMENT -> R.string.notification_comment_content
        LIKE    -> TODO()
        FOLLOW  -> TODO()
        MISSION -> R.string.notification_mission_content
        UNLOCK  -> TODO()
    }
}