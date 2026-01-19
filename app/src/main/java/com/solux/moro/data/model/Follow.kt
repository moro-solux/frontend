package com.solux.moro.data.model

data class FollowUserResponse(
    val content: List<FollowUserDto>,
    val currentPage: Int,
    val totalPages: Int,
    val hasNext: Boolean
)

data class FollowUserDto(
    val followId: Long,
    val status: String,
    val userId: Long,
    val userName: String
)

data class UserInfo(
    val userId: Long,
    val userName: String,
    val isFollowing: Boolean=true
)