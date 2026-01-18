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


data class TokenRequest(
    val fcmToken: String
)

data class SSEResponse(
    val timeout:String
)

data class LikeResponseDto(
    val totalCount: Int,
    val topLiker: TopLikerDto?
)

data class TopLikerDto(
    val userId: Long,
    val nickname: String
)