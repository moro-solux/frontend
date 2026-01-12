package com.solux.moro.data.dto

data class UserProfileResponse(
    val data: UserProfileDto,
    val message: String,
    val status: Int,
    val success: Boolean
)

data class UserProfileDto(
    val userId: Long,
    val userName: String,
    val userColorHex: String?,      // 비공개 시 null
    val colorCount: Int,
    val followerCount: Int,
    val followingCount: Int,        // 응답 오타(followerCount 2번)는 백엔드와 맞추되 보통 followingCount임
    val colorCodes: List<ColorCodeDto>, // 비공개 시 [] (빈 리스트)
    val currentUser: Boolean,
    val visible: Boolean            // 프로필 공개 여부 핵심 필드
)

data class ColorCodeDto(
    val colorId: Long,
    val hexCode: String
)