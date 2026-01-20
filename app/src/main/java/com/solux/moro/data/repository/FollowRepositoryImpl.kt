package com.solux.moro.data.repository

import android.util.Log
import com.solux.moro.core.domain.FollowRepository
import com.solux.moro.data.mapper.toUiModel
import com.solux.moro.data.model.FollowStatusResponse
import com.solux.moro.data.model.FollowUserDto
import com.solux.moro.data.model.UserInfo
import com.solux.moro.data.service.FollowService
import com.solux.moro.data.service.UserService
import jakarta.inject.Inject
import retrofit2.HttpException

class FollowRepositoryImpl@Inject constructor(
    private val followService: FollowService,
    private val userService: UserService
): FollowRepository {
    override suspend fun followRequest(userId: Long): Result<FollowStatusResponse> {//팔로우 요청
        return try {
            val response = followService.followRequest(userId)
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
        if (response.success) { val uiModels = response.data.map { it.toUiModel() }

            Result.success(uiModels)
        } else {
            Result.failure(Exception(response.message))
        }
    } catch (e: Exception) { Result.failure(e) }


    override suspend fun getFollowings(userId: Long): Result<List<UserInfo>> {
        return try {
            val response = userService.getFollowings(userId)
            if (response.success) {
                val uiModels = response.data.map { it.toUiModel() }
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
                val dataList: List<FollowUserDto>? =response.data ?: emptyList()
                val uiModels = dataList?.map { item: FollowUserDto ->
                    item.toUiModel()
                } ?: emptyList()
                //Log.d("FollowRequestVM","팔로우 요청 리스트 불러오기 성공 ${uiModels}")
                Result.success(uiModels)
            } else {
                //Log.d("FollowRequestVM","팔로우 요청 리스트 불러오기 실패")
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            //Log.d("FollowRequestVM","팔로우 요청 리스트 에러 ${e}")
            Result.failure(e)
        }
    }
    override suspend fun acceptFollowRequest(userId: Long): Result<Unit> =
    try {
        val response = followService.acceptRequest(userId)
        if (response.success) {
            Log.d("followImpl", "팔로우 승인 성공!! 서버 메시지: ${response.message}")
            Log.d("followImpl", "응답 데이터: ${response.data}")
            Result.success(Unit)}
        else Result.failure(Exception(response.message))
    } catch (e: HttpException) {
        val errorBody = e.response()?.errorBody()?.string()
        Log.d("followImpl", "400 에러 상세 내용: $errorBody")
        Result.failure(e)
    }catch (e: Exception) {
    Log.d("followImpl", "기타 에러: $e")
    Result.failure(e)
}


    override suspend fun rejectFollowRequest(userId: Long): Result<Unit> = try {
        val response = followService.rejectRequest(userId)
        if (response.success) Result.success(Unit)
        else Result.failure(Exception(response.message))
    } catch (e: Exception) { Result.failure(e) }

    override suspend fun unFollow(userId: Long): Result<Unit> = try {
        val response = followService.deleteFollowing(userId)
        if (response.success) Result.success(Unit)
        else Result.failure(Exception(response.message))
    } catch (e: Exception) { Result.failure(e) }

    override suspend fun deleteFollower(userId: Long): Result<Unit> = try {
        val response = userService.deleteFollower(userId)
        if (response.success) Result.success(Unit)
        else Result.failure(Exception(response.message))
    } catch (e: Exception) { Result.failure(e) }
}