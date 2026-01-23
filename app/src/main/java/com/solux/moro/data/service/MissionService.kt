package com.solux.moro.data.service

import com.solux.moro.data.dto.request.CommentEditRequest
import com.solux.moro.data.dto.request.CommentRequest
import com.solux.moro.data.dto.response.CurrentMissionDto
import com.solux.moro.data.dto.response.MissionAnalysisDto
import com.solux.moro.data.dto.response.MissionBaseResponse
import com.solux.moro.data.dto.response.MissionCommentDto
import com.solux.moro.data.dto.response.MissionPostDto
import com.solux.moro.data.dto.response.ShareUrlDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface MissionService {


    @GET("/api/missions/now")
    suspend fun getTodayMission(): Response<MissionBaseResponse<CurrentMissionDto>>


    @GET("/api/missions/posts")
    suspend fun getAllMissionPosts(): Response<MissionBaseResponse<List<MissionPostDto>>>


    @GET("/api/missions/posts/me")
    suspend fun getMyMissionPosts(): Response<MissionBaseResponse<List<MissionPostDto>>>


    @GET("/api/missions/posts/friends")
    suspend fun getFriendsMissionPosts(): Response<MissionBaseResponse<List<MissionPostDto>>>


    @Multipart
    @POST("/api/missions/upload")
    suspend fun uploadMissionPost(
        @Part image: MultipartBody.Part,
        @Part("data") data: RequestBody // ★ 여기가 "data"여야 Swagger와 일치합니다!
    ): Response<MissionBaseResponse<MissionPostDto>>



    @DELETE("/api/missions/posts/{misPostId}/delete")
    suspend fun deleteMissionPost(
        @Path("misPostId") misPostId: Long
    ): Response<MissionBaseResponse<Unit>>


    @POST("/api/missions/posts/comments")
    suspend fun createComment(
        @Body request: CommentRequest
    ): Response<MissionBaseResponse<Unit>>


    @GET("/api/missions/posts/{misPostId}/comments")
    suspend fun getComments(
        @Path("misPostId") misPostId: Long
    ): Response<MissionBaseResponse<List<MissionCommentDto>>>


    @PATCH("/api/missions/posts/comments/{misCommentId}/edit")
    suspend fun editComment(
        @Path("misCommentId") misCommentId: Long,
        @Body request: CommentEditRequest
    ): Response<MissionBaseResponse<Unit>>


    @GET("/api/missions/posts/{misPostId}/share")
    suspend fun getShareUrl(
        @Path("misPostId") misPostId: Long
    ): Response<MissionBaseResponse<ShareUrlDto>>


    @DELETE("/api/missions/posts/comments/{misCommentId}/delete")
    suspend fun deleteComment(
        @Path("misCommentId") misCommentId: Long
    ): Response<MissionBaseResponse<Unit>>

    @Multipart
    @POST("/api/missions/analyze-preview/{missionId}")
    suspend fun analyzeMissionImage(
        @Path("missionId") missionId: Long,
        @Part image: MultipartBody.Part
    ): Response<MissionBaseResponse<MissionAnalysisDto>>
}