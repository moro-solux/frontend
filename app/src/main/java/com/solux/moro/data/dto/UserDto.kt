package com.solux.moro.data.dto

data class UserProfileResponse<T>(
    val success: Boolean,
    val status: Int,
    val message: String,
    val data: T? = null
)

data class UserProfileEditRequest(
    val userName: String,
    val userColorId: Long,
    val userColorHex: String?
)
data class MainColorEditRequest(
    val colorIds: List<Long>
)
data class UserProfileDto(
    val userId: Long,
    val userName: String,
    val userColorHex: String?,
    val colorCount: Int,
    val followerCount: Int,
    val followingCount: Int,
    val colorCodes: List<ColorCodeDto>,
    val currentUser: Boolean,
    val visible: Boolean
)

data class ColorCodeDto(
    val colorId: Long,
    val hexCode: String
)