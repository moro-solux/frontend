package com.solux.moro.core.domain

import com.solux.moro.data.dto.TargetUserIdDto
import com.solux.moro.data.model.FollowStatusResponse
import com.solux.moro.data.model.UserInfo

interface FollowRepository {
    suspend fun followRequest(userId: TargetUserIdDto): Result<FollowStatusResponse>//팔로우 걸기
    suspend fun getFollowers(
         userId: Long,
         page: Int,
         size: Int,
         keyWord: String
    ): Result<List<UserInfo>>
    suspend fun getFollowings(userId: Long): Result<List<UserInfo>>

    suspend fun getFollowRequest(): Result<List<UserInfo>>
    suspend fun acceptFollowRequest(userId: Long): Result<Unit>
    suspend fun rejectFollowRequest(userId: Long): Result<Unit>

    suspend fun unFollow(userId: Long): Result<Unit>
    //언팔로우 (내 팔로잉 목록에서 삭제
    suspend fun deleteFollower(userId: Long): Result<Unit>
    //팔로워 삭제(내 팔로워 목록에서 삭제
}


