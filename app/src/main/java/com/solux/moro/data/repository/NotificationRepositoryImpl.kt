package com.solux.moro.data.repository

import android.util.Log
import com.solux.moro.core.domain.NotificationRepository
import com.solux.moro.data.dto.BaseResponse
import com.solux.moro.data.dto.TokenRequest
import com.solux.moro.data.mapper.toUiModel
import com.solux.moro.data.model.NotificationUiModel
import com.solux.moro.data.service.NotificationService
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources
import java.util.concurrent.TimeUnit

class NotificationRepositoryImpl @Inject constructor(
    private val notificationService: NotificationService
): NotificationRepository{

    private val _sseEvents = MutableSharedFlow<String>()
    override val sseEvents: SharedFlow<String> = _sseEvents.asSharedFlow()

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

    override fun connectNotificationStream(token: String?) {
        val mytoken= token
        val request = Request.Builder()
            .url("https://moro-be.store/api/notifications/stream")
            .header("Accept", "text/event-stream")
            .header("Authorization", "Bearer $mytoken")
            .build()


        val listener = object : EventSourceListener() {
            override fun onOpen(eventSource: EventSource, response: Response) {
                Log.d("SSE", "연결 성공! 이제 데이터를 기다립니다.")
            }
            override fun onEvent(
                eventSource: okhttp3.sse.EventSource,
                id: String?,
                type: String?,
                data: String
            ) {
                Log.d("SSE", "실시간 알림 도착: $data")
                CoroutineScope(Dispatchers.IO).launch {
                    _sseEvents.emit(data)
                }
            }
            override fun onFailure(eventSource: EventSource, t: Throwable?, response: Response?) {
                Log.e("SSE", "연결 에러 발생: ${t?.message}")
                Log.e("SSE", "응답 코드: ${response?.code}")
            }
        }

        val client = OkHttpClient.Builder()
            .connectTimeout(0, TimeUnit.MILLISECONDS)
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .build()

        EventSources.createFactory(client).newEventSource(request, listener)
    }

    override suspend fun getPrivacyStatus(): Boolean {
        return try {
            val response = notificationService.getPrivacyStatus()
            if (response.success) {
                Log.d("NotificationRepo", "getPrivacyStatus 성공: ${response.success}")
                response.data.isPublic
            }
            else false
        }
        catch  (e: Exception){
            Log.e("NotificationRepo", "getPrivacyStatus 실패: ${e.message}")
            false
        }
    }
}