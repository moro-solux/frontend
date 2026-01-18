package com.solux.moro.data.repository

import android.util.Log
import com.solux.moro.core.domain.NotificationRepository
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
            val mappedData = response.data.mapValues { entry ->
                entry.value.map { dto ->
                    dto.toUiModel()
                }
            }
            emit(mappedData)
        }
    }
    catch  (e: Exception){
        Log.e("NotificationRepo", "목록 조회 실패: ${e.message}")
    }
}


    override suspend fun  markAsRead(notificationId: Long){
        try {
            val response = notificationService.readNotification(notificationId)
            if (response.success) {
                Log.d("NotificationRepo", "$notificationId 번 알림 읽음 처리")
            }
        }
        catch  (e: Exception){
            Log.e("NotificationRepo", "읽음 처리 실패: ${e.message}")
        }
    }
}