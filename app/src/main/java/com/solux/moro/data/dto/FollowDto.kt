package com.solux.moro.data.dto

data class TargetUserIdDto(
    val targetUserId:Long,
)
data class UserIDDto(
    val userId:Long,
)

data class FollowId(
    val followId:Long,
)

data class SearchUserDto(
    val nickname:String,
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



