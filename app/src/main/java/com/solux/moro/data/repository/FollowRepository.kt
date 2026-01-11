package com.solux.moro.data.repository

import com.solux.moro.ui.followlist.FollowUserInfo

interface FollowRepository {

    suspend fun getFollowers(): List<FollowUserInfo>
    suspend fun getFollowings(): List<FollowUserInfo>

    suspend fun getFollowRequest(): List<FollowUserInfo>
    suspend fun acceptFollowRequest(userId: Long)
    suspend fun rejectFollowRequest(userId: Long)

    suspend fun unFollow(userId: Long)
    //언팔로우 (팔로잉 목록에서 following -> follow
    suspend fun deleteFollower(userId: Long)
    //팔로워 삭제( 팔로워 목록에서 삭제
}


