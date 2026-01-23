package com.solux.moro.data.repository.mission

import com.solux.moro.data.dto.request.CommentEditRequest
import com.solux.moro.data.dto.request.CommentRequest
import com.solux.moro.data.dto.response.MissionAnalysisDto
import com.solux.moro.data.dto.response.MissionBaseResponse
import com.solux.moro.data.service.MissionService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class MissionRepositoryImpl @Inject constructor(
    private val service: MissionService
) : MissionRepository {
    override suspend fun getTodayMission() = service.getTodayMission()
    override suspend fun getAllMissionPosts() = service.getAllMissionPosts()
    override suspend fun getMyMissionPosts() = service.getMyMissionPosts()
    override suspend fun getFriendsMissionPosts() = service.getFriendsMissionPosts()
    override suspend fun uploadMissionPost(image: MultipartBody.Part, data: RequestBody) = service.uploadMissionPost(image, data)
    override suspend fun deleteMissionPost(misPostId: Long) = service.deleteMissionPost(misPostId)
    override suspend fun createComment(request: CommentRequest) = service.createComment(request)
    override suspend fun getComments(misPostId: Long) = service.getComments(misPostId)
    override suspend fun editComment(misCommentId: Long, newContent: String) = service.editComment(misCommentId, CommentEditRequest(newContent))
    override suspend fun getShareUrl(misPostId: Long) = service.getShareUrl(misPostId)


    override suspend fun analyzeMissionImage(missionId: Long, image: MultipartBody.Part): Response<MissionBaseResponse<MissionAnalysisDto>> { return service.analyzeMissionImage(missionId, image) }

    override suspend fun editComment(misCommentId: Long, request: CommentEditRequest): Response<MissionBaseResponse<Unit>> { return service.editComment(misCommentId, request) }

    override suspend fun deleteComment(misCommentId: Long) = service.deleteComment(misCommentId)
}