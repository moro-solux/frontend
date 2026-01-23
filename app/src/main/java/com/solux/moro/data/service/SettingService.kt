package com.solux.moro.data.service

import com.solux.moro.data.dto.request.NotificationRequestDto
import com.solux.moro.data.dto.request.PrivacyRequestDto
import com.solux.moro.data.dto.response.MissionBaseResponse
import com.solux.moro.data.dto.response.NotificationStatusDto
import com.solux.moro.data.dto.response.PrivacyStatusDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface SettingService {

    // 공개/비공개 설정
    @PATCH("/api/settings/privacy")
    suspend fun updatePrivacy(
        @Body body: PrivacyRequestDto
    ): Response<Unit>

    // 알림 설정
    @PATCH("/api/settings/notification")
    suspend fun updateNotification(
        @Body body: NotificationRequestDto
    ): Response<Unit>

    // 로그아웃
    @POST("/api/auth/logout")
    suspend fun logout(): Response<Unit>

    @GET("/api/settings/privacy-status")
    suspend fun getPrivacyStatus(): Response<MissionBaseResponse<PrivacyStatusDto>>

    @GET("/api/settings/notification-status")
    suspend fun getNotificationStatus(): Response<MissionBaseResponse<NotificationStatusDto>>
}
