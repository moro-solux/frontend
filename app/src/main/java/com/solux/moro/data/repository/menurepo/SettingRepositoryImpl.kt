package com.solux.moro.data.repository.menurepo

import com.solux.moro.data.dto.request.NotificationRequestDto
import com.solux.moro.data.dto.request.PrivacyRequestDto
import com.solux.moro.data.service.SettingService
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SettingRepositoryImpl @Inject constructor(
    private val settingService: SettingService
) : SettingRepository {

    // Swagger에서 복사한 토큰
    private val ACCESS_TOKEN = ""

    override suspend fun logout(): Result<Unit> {
        return try {
            val response = settingService.logout(ACCESS_TOKEN)
            if (response.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("로그아웃 실패"))
        } catch (e: Exception) { Result.failure(e) }
    }

    override suspend fun updateVisibility(visible: Boolean): Result<Unit> {
        return try {
            val response = settingService.updatePrivacy(ACCESS_TOKEN, PrivacyRequestDto(visible))
            if (response.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("공개 설정 변경 실패"))
        } catch (e: Exception) { Result.failure(e) }
    }

    // 알림 설정 기능
    override suspend fun updateNotification(enabled: Boolean): Result<Unit> {
        return try {
            val response = settingService.updateNotification(
                token = ACCESS_TOKEN,
                body = NotificationRequestDto(isNotification = enabled)
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("알림 설정 실패"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getVisibility(): Flow<Boolean> = flowOf(true)
}