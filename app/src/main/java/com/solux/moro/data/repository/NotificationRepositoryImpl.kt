package com.solux.moro.data.repository

import android.util.Log
import com.solux.moro.core.domain.NotificationRepository
import com.solux.moro.data.dto.BaseResponse
import com.solux.moro.data.dto.TokenRequest
import com.solux.moro.data.mapper.toUiModel
import com.solux.moro.data.model.NotificationUiModel
import com.solux.moro.data.service.NotificationService
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NotificationRepositoryImpl @Inject constructor(
    private val notificationService: NotificationService
): NotificationRepository{
    override suspend fun getNotifications(): Flow<Map<String, List<NotificationUiModel>>> = flow{
    try {
        val response = notificationService.getNotifications()
        if (response.success) {
            Log.d("NotificationRepo", "서버 응답 성공 여부: ${response.success}")
            val mappedData = response.data.mapValues { entry ->
                entry.value.map { dto ->
                    dto.toUiModel()
                }
            }
            Log.d("NotificationRepo", "매핑된 데이터: ${mappedData.values}")
            emit(mappedData)
        }
    }
    catch  (e: Exception){
        Log.e("NotificationRepo", "목록 조회 실패: ${e.message}")
    }
}


    override suspend fun markAsRead(notificationId: Long){
        try {
            Log.d("NotificationRepo", "요청 ID: $notificationId")
            val response = notificationService.readNotification(notificationId)
            if (response.success) {
                Log.d("NotificationRepo", "$notificationId 번 알림 읽음 처리")
            }
        }
        catch  (e: Exception){
            Log.e("NotificationRepo", "읽음 처리 실패: ${e.message}")
        }
    }

    override suspend fun getLikes(postId: Long):Int{
        return try{
            val response = notificationService.getLikes(postId)
            if (response.success) {
                Log.d("NotificationRepo", "좋아요 수 조회 성공 여부: ${response.success}")
                response.data?.totalCount ?: 1
            } else 1
        }
        catch  (e: Exception){
            Log.e("NotificationRepo", "좋아요 수 조회 실패: ${e.message}")
            1
        }
    }

    override suspend fun postToken(fcmToken: String): BaseResponse<String> {
        return try {
            val request = TokenRequest(fcmToken = fcmToken)

            val response = notificationService.postToken(request)

            if (response.success) {
                Log.d("NotificationRepo", "FCM 토큰 등록 성공: ${response.data}")
            }
            response
        } catch (e: Exception) {
            Log.e("NotificationRepo", "토큰 등록 에러: ${e.message}")
            BaseResponse(status = 500, success = false, message = e.message ?: "Error", data = "")
        }
    }

    override suspend fun deleteToken(token: String): BaseResponse<String> {
        return try {
            val response = notificationService.deleteToken(token)

            if (response.success) {
                Log.d("NotificationRepo", "FCM 토큰 제거 성공: ${response.data}")
            }
            response
        } catch (e: Exception) {
            Log.e("NotificationRepo", "토큰 제거 에러: ${e.message}")
            BaseResponse(status = 500, success = false, message = e.message ?: "Error", data = "")
        }
    }
}