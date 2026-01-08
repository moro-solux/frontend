package com.solux.moro.data.model

import java.time.Instant

data class NotificationItem(
    val type: String,
    val name: String?,
    val content: String?,
    val createdAt: String
)
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
