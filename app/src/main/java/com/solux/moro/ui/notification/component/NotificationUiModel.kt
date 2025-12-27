package com.solux.moro.ui.notification.component

import java.time.Instant

sealed class NotificationUiModel {
    abstract val createdAt: Instant

    data class Comment(
        val name: String,
        val content: String,
        override val createdAt: Instant
    ) : NotificationUiModel()

    data class Like(
        override val createdAt: Instant
    ) : NotificationUiModel()

    data class Follow(
        override val createdAt: Instant
    ) : NotificationUiModel()
}
