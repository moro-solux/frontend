package com.solux.moro.data.repository.upload

import com.solux.moro.data.dto.request.CaptureResultDto
import com.solux.moro.data.dto.request.ColorRequestDto
import com.solux.moro.data.dto.request.LocationRequestDto
import com.solux.moro.data.repository.UploadRepository
import com.solux.moro.data.service.UploadService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class UploadRepositoryImpl @Inject constructor(
    private val uploadService: UploadService
) : UploadRepository {

    private val ACCESS_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqanVzc2hAc29va215dW5nLmFjLmtyIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE3Njg1NzYyMTUsImV4cCI6MTc2ODU3OTgxNX0.Cf4Ryw0qkPuUjD4xFWVLybjjkvEMg0tC2pP5LSnovno"

    override suspend fun capturePost(imageFile: File, lat: Double, lng: Double): Result<CaptureResultDto> {
        return try {
            // 파일을 RequestBody로 변환 (이미지 타입 지정)
            val requestFile = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())

            // MultipartBody.Part로
            val body = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)

            // 서비스 호출 (파일 + 쿼리 파라미터)
            val response = uploadService.capturePost(
                token = ACCESS_TOKEN,
                image = body,
                lat = lat,
                lng = lng
            )

            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(response.body()!!.data!!)
            } else {
                val errorMsg = response.errorBody()?.string() ?: "업로드 실패: ${response.code()}"
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateLocation(draftId: Long, lat: Double, lng: Double, address: String): Result<Unit> {
        return try {
            val request = LocationRequestDto(lat, lng, address)
            val response = uploadService.updateLocation(ACCESS_TOKEN, draftId, request)
            if (response.isSuccessful && response.body()?.success == true) Result.success(Unit)
            else Result.failure(Exception("Location update failed"))
        } catch (e: Exception) { Result.failure(e) }
    }

    override suspend fun updateMainColor(draftId: Long, colorId: Int): Result<Unit> {
        return try {
            val request = ColorRequestDto(colorId)
            val response = uploadService.updateMainColor(ACCESS_TOKEN, draftId, request)
            if (response.isSuccessful && response.body()?.success == true) Result.success(Unit)
            else Result.failure(Exception("Color update failed"))
        } catch (e: Exception) { Result.failure(e) }
    }

    override suspend fun publishPost(draftId: Long): Result<Unit> {
        return try {
            val response = uploadService.publishPost(ACCESS_TOKEN, draftId)

            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(Unit)
            } else {
                val errorMsg = response.errorBody()?.string() ?: "Publish failed"
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}