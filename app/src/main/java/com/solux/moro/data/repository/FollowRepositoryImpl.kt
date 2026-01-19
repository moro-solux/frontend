package com.solux.moro.data.repository

import com.solux.moro.core.domain.FollowRepository
import com.solux.moro.data.dto.TargetUserIdDto
import com.solux.moro.data.mapper.toUiModel
import com.solux.moro.data.model.UserInfo
import com.solux.moro.data.service.FollowService
import com.solux.moro.data.service.UserService
import jakarta.inject.Inject

class FollowRepositoryImpl@Inject constructor(
    private val followService: FollowService,
    private val userService: UserService
): FollowRepository {
    override suspend fun followRequest(targetUserId: TargetUserIdDto): Result<UserInfo> {//팔로우 요청
        return try {
            val response = followService.followRequest(targetUserId)
            if (response.success) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun getFollowers(
        userId: Long, page: Int, size: Int, keyWord: String
    ): Result<List<UserInfo>> = try {
        val response = userService.getFollowers(userId, page, size, keyWord)
        if (response.success) {
            // content 리스트를 꺼내서 변환
            val uiModels = response.data?.content?.map { it.toUiModel() } ?: emptyList()
            Result.success(uiModels)
        } else {
            Result.failure(Exception(response.message))
        }
    } catch (e: Exception) { Result.failure(e) }


    override suspend fun getFollowings(userId: Long): Result<List<UserInfo>> {
        return try {
            val response = userService.getFollowings(userId)
            if (response.success) {
                val uiModels = response.data?.content?.map { it.toUiModel() } ?: emptyList()
                Result.success(uiModels)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
    override suspend fun getFollowRequest(): Result<List<UserInfo>> {
        return try {
            val response =  userService.getFollowRequests()
            if (response.success) {
                val uiModels = response.data?.content?.map { it.toUiModel() } ?: emptyList()
                Result.success(uiModels)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun acceptFollowRequest(userId: Long): Result<Unit> = try {
        val response = followService.acceptRequest(userId)
        if (response.success) Result.success(Unit)
        else Result.failure(Exception(response.message))
    } catch (e: Exception) { Result.failure(e) }

    override suspend fun rejectFollowRequest(followId: Long): Result<Unit> = try {
        val response = followService.rejectRequest(followId)
        if (response.success) Result.success(Unit)
        else Result.failure(Exception(response.message))
    } catch (e: Exception) { Result.failure(e) }

    override suspend fun unFollow(targetUserId: Long): Result<Unit> = try {
        val response = followService.deleteFollowing(targetUserId)
        if (response.success) Result.success(Unit)
        else Result.failure(Exception(response.message))
    } catch (e: Exception) { Result.failure(e) }

    override suspend fun deleteFollower(targetUserId: Long): Result<Unit> = try {
        val response = userService.deleteFollower(targetUserId)
        if (response.success) Result.success(Unit)
        else Result.failure(Exception(response.message))
    } catch (e: Exception) { Result.failure(e) }
}