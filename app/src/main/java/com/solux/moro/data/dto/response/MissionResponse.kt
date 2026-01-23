package com.solux.moro.data.dto.response



data class MissionBaseResponse<T>(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: T?
)


data class CurrentMissionDto(
    val missionId: Long,
    val missionTitle: String,
    val missionType: Boolean,
    val targetColor: String,
    val createdAt: String
)


data class MissionPostDto(
    val misPostId: Long,
    val missionTitle: String,
    val userName: String,
    val imageUrl: String,
    val detail: String,
    val createdAt: String
)


data class MissionCommentDto(
    val misCommentId: Long,
    val misContent: String,
    val username: String,
    val misCreatedAt: String
)


data class ShareUrlDto(
    val misPostId: Long,
    val shareUrl: String
)


data class MissionAnalysisDto(
    val score: Double // 예: 87.3
)