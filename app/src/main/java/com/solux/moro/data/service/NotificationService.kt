package com.solux.moro.data.service

import com.solux.moro.data.dto.BaseResponse
import com.solux.moro.data.dto.NotificationDto
import com.solux.moro.data.dto.SSEResponse
import com.solux.moro.data.dto.TokenRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface NotificationService {
    @PUT("/api/notifications/{notificationId}/read")
    suspend fun readNotification(
        @Path("notificationId") notificationId: Long
    ): BaseResponse<String>

    @POST("/api/notifications/token")// 토큰 등록
    suspend fun postToken(
        @Body tokenRequest: TokenRequest
    ): BaseResponse<String>

    @DELETE("/api/notifications/token")// 토큰 제거
    suspend fun deleteToken(
        @Query("token") token: String
    ): BaseResponse<String>

    @GET("/api/notifications")// 알림 목록 조회
    suspend fun getNotifications(
    ): BaseResponse<Map<String, List<NotificationDto>>>
    @GET("/api/notifications/stream")// 알림 스트림 연결
    suspend fun getSSE(
    ): SSEResponse
}