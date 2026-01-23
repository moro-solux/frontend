package com.solux.moro.data.service

import com.solux.moro.data.dto.BaseResponse
import com.solux.moro.data.model.CommentDto
import com.solux.moro.data.model.CommentRequest
import com.solux.moro.data.model.FeedData
import com.solux.moro.data.model.LikeDto
import com.solux.moro.data.model.PostDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FeedService {
    @GET("/api/posts/feed") // 게시물 목록 조회
    suspend fun getFeed(
        @Query("page") page: Int=0,
        @Query("size") size: Int=0,
        @Query("sort") sort: String = "createdAt,desc"
    ): BaseResponse<FeedData>

    @GET("/api/posts/{postId}")//개별 게시물 조회
    suspend fun getPost(
        @Path("postId") postId: Long,
    ): BaseResponse<PostDto>

    @DELETE("/api/posts/{postId}")
    suspend fun deletePost(
        @Path("postId") postId: Long,
    ): BaseResponse<Unit>

    @POST("/api/posts/{postId}/likes")
    suspend fun likeFeed(
        @Path("postId") postId: Long,
    ): BaseResponse<Unit>

    @GET("/api/posts/{postId}/likes")
    suspend fun getLike(
        @Path("postId") postId: Long,
    ): BaseResponse<LikeDto>

    @POST("/api/posts/{postId}/comments")
    suspend fun addComment(
        @Path("postId") postId: Long,
        @Body content: CommentRequest,
    ): BaseResponse<Int>

    @GET("/api/posts/{postId}/comments")
    suspend fun getComments(
        @Path("postId") postId: Long,
    ): BaseResponse<List<CommentDto>>
}