package com.solux.moro.data.dto

data class FollowRequestDto(
    val targetUserId:Long,

)

data class FollowAcceptDto(
    val followId:Long,
    val status: String
)

data class FollowUserInfoDto(
    val followId: Long,
    val status: String,
    val userId: Long,
    val userName: String
)


