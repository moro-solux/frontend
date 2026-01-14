package com.solux.moro.data.dto

data class UserProfileResponse<T>(
    val success: Boolean,
    val status: Int,
    val message: String,
    val data: T? = null
)

data class UserProfileEditRequest(
    val userName: String? = null,
    val userColorId: Int? = null,
    val userColorHex: String? = null
)
data class MainColorEditRequest(
    val colorIds: List<Int>
)
data class UserProfileDto(
    val userId: Long,
    val userName: String,
    val userColorHex: String?,
    val colorCount: Int,
    val followingStatus:String,
    val followerCount: Int,
    val followingCount: Int,
    val colorCodes: List<String>,
    val currentUser: Boolean,
    val visible: Boolean
)

data class ColorCodeDto(
    val colorId: Long,
    val hexCode: String
)

