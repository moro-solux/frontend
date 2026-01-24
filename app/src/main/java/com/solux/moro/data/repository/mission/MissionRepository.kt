package com.solux.moro.data.repository.mission


import com.solux.moro.data.dto.request.CommentEditRequest
import com.solux.moro.data.dto.request.CommentRequest
import com.solux.moro.data.dto.response.CurrentMissionDto
import com.solux.moro.data.dto.response.MissionAnalysisDto
import com.solux.moro.data.dto.response.MissionBaseResponse
import com.solux.moro.data.dto.response.MissionBaseResponse

.*
import com.solux.moro.data.dto.response.MissionCommentDto
import com.solux.moro.data.dto.response.MissionPostDto
import com.solux.moro.data.dto.response.ShareUrlDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface MissionRepository {
    suspend fun getTodayMission(): Response<MissionBaseResponse<CurrentMissionDto>>
    suspend fun getAllMissionPosts(): Response<MissionBaseResponse<List<MissionPostDto>>>
    suspend fun getMyMissionPosts(): Response<MissionBaseResponse<List<MissionPostDto>>>
    suspend fun getFriendsMissionPosts(): Response<MissionBaseResponse<List<MissionPostDto>>>
    suspend fun uploadMissionPost(image: MultipartBody.Part, data: RequestBody): Response<MissionBaseResponse<MissionPostDto>>
    suspend fun deleteMissionPost(misPostId: Long): Response<MissionBaseResponse<Unit>>
    suspend fun createComment(request: CommentRequest): Response<MissionBaseResponse<Unit>>
    suspend fun getComments(misPostId: Long): Response<MissionBaseResponse<List<MissionCommentDto>>>
    suspend fun editComment(misCommentId: Long, newContent: String): Response<MissionBaseResponse<Unit>>
    suspend fun getShareUrl(misPostId: Long): Response<MissionBaseResponse<ShareUrlDto>>

    suspend fun deleteComment(misCommentId: Long): Response<MissionBaseResponse<Unit>>

    suspend fun analyzeMissionImage(missionId: Long, image: MultipartBody.Part): Response<MissionBaseResponse<MissionAnalysisDto>>

    suspend fun editComment(misCommentId: Long, request: CommentEditRequest): Response<MissionBaseResponse<Unit>>
}