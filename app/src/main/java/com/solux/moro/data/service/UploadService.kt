package com.solux.moro.data.service

import com.solux.moro.data.dto.request.CaptureResultDto
import com.solux.moro.data.dto.request.ColorRequestDto
import com.solux.moro.data.dto.request.LocationRequestDto
import com.solux.moro.data.dto.response.UploadBaseResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface UploadService {

    // 사진 전송
    @Multipart
    @POST("api/posts/actions/capture")
    suspend fun capturePost(
        @Part image: MultipartBody.Part,
        @Query("lat") lat: Double,
        @Query("lng") lng: Double
    ): Response<UploadBaseResponse<CaptureResultDto>>

    // 위치 수정
    @PATCH("api/posts/drafts/{draftId}/location")
    suspend fun updateLocation(
        @Path("draftId") draftId: Long,
        @Body request: LocationRequestDto
    ): Response<UploadBaseResponse<Unit>>

    // 색상 수정
    @PATCH("api/posts/drafts/{draftId}/main-color")
    suspend fun updateMainColor(
        @Path("draftId") draftId: Long,
        @Body request: ColorRequestDto
    ): Response<UploadBaseResponse<Unit>>

    // 게시
    @POST("api/posts/drafts/{draftId}/publish")
    suspend fun publishPost(
        @Path("draftId") draftId: Long,
        @Body body: Map<String, String> = emptyMap()
    ): Response<UploadBaseResponse<Long>>
}
