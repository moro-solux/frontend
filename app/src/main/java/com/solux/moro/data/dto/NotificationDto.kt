package com.solux.moro.data.dto

data class NotificationResponse(
    val data: Map<String, List<NotificationDto>>,
    val message: String,
    val status: Int,
    val success: Boolean
)

data class NotificationDto(
    val id: Long,
    val type: String,
    val content: Map<String, Any?>?,
    val read: Boolean,
    val createdAt: String
)