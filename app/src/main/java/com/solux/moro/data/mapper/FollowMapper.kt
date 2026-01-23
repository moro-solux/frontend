package com.solux.moro.data.mapper

import com.solux.moro.data.model.FollowUserDto
import com.solux.moro.data.model.UserInfo

fun FollowUserDto.toUiModel(): UserInfo {
    return UserInfo(
        userId = this.userId,
        userName = this.userName,
        followId = this.followId
    )
}
