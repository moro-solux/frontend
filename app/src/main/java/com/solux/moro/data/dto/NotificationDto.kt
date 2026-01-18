package com.solux.moro.data.dto

import com.google.gson.annotations.SerializedName
data class NotificationDto(
    val id: Long,
    val type: String,
    val content: Map<String, Any?>?,
    @SerializedName("read")
    val isRead: Boolean,
    val createdAt: String
)

data class NotificationContent(
    val additionalProp1: String?,
    val additionalProp2: String?,
    val additionalProp3: String?
)

data class TokenRequest(
    val token: String
)

data class SSEResponse(
    val timeout:String
)