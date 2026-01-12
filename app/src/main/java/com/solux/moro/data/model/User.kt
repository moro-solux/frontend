package com.solux.moro.data.model

data class User(
    val id: Long,
    val email: String,
    val nickname: String,
    val colorPalette: UserColorPalette,
    val visible: Boolean = true
    )

data class UserStats(
    val colorsCount: Int,
    val followerCount: Int,
    val followingCount: Int,
    val isFollowing: Boolean
)

