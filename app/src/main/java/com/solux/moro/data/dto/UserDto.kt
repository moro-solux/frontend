package com.solux.moro.data.dto

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

data class ColorThemeDto(
    val themeName: String,
    val colors: List<ColorUnlockDto>
)

data class ColorUnlockDto(
    val colorId: Int,
    val hexCode: String,
    val postCount: Long,
    val unlocked: Boolean,
    val isRepresentative: Boolean
)


