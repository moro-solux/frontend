package com.solux.moro.data.repository

import com.solux.moro.data.dto.request.CaptureResultDto
import java.io.File

interface UploadRepository {
    suspend fun capturePost(imageFile: File, lat: Double, lng: Double): Result<CaptureResultDto>
    suspend fun updateLocation(draftId: Long, lat: Double, lng: Double, address: String): Result<Unit>
    suspend fun updateMainColor(draftId: Long, colorId: Int): Result<Unit>
    suspend fun publishPost(draftId: Long): Result<Unit>
}