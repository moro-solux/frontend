package com.solux.moro.data.service

import com.solux.moro.data.dto.BaseResponse
import com.solux.moro.data.model.FollowStatusResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface FollowService {
    @POST("/api/follows")//팔로우 요청
    suspend fun followRequest(
        @Body targetUserId: Long
    ):BaseResponse<FollowStatusResponse>

    @PATCH("/api/follows/{followId}/accept")//팔로우 승인
    suspend fun acceptRequest(
        @Path("followId") followId: Long
    ):BaseResponse<FollowStatusResponse>

    @DELETE("/api/follows/{targetUserId}")//내가 한 팔로우 취소/언팔
    suspend fun deleteFollowing(
        @Path("targetUserId") targetUserId: Long
    ):BaseResponse<String>

    @DELETE("/api/follows/{followId}/reject")//나에게 온 팔로우 요청 거절
    suspend fun rejectRequest(
        @Path("followId") followId: Long
    ):BaseResponse<String>

}