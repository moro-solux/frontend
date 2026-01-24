package com.solux.moro.data.repository.menurepo

import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    // 로그아웃
    suspend fun logout(): Result<Unit>

    // 공개 설정
    suspend fun updateVisibility(visible: Boolean): Result<Unit>

    // 현재 공개 설정 상태를
    fun getVisibility(): Flow<Boolean>


    // 알림
    suspend fun updateNotification(enabled: Boolean): Result<Unit>

    suspend fun getPrivacyStatus(): Result<Boolean>
    suspend fun getNotificationStatus(): Result<Boolean>
}