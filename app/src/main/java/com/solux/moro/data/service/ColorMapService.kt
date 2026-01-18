package com.solux.moro.data.service

import com.solux.moro.data.dto.response.ColorMapListResponse
import com.solux.moro.data.dto.response.ColorPostsResponse
import com.solux.moro.data.dto.response.ColorUpdateResponse
import com.solux.moro.data.dto.response.PostDetailResponse
import com.solux.moro.data.dto.response.SingleThemeResponse
import retrofit2.Response
import retrofit2.http.*

interface ColorMapService {
    // 전체 컬러맵 정보 조회
    @GET("/api/colormaps")
    suspend fun getAllColorMaps(): Response<ColorMapListResponse>

    // 특정 테마 정보 조회
    @GET("/api/colormaps/themes/{themeName}")
    suspend fun getThemeDetails(
        @Path("themeName") themeName: String
    ): Response<SingleThemeResponse>

    // 특정 색상의 포스트 목록 조회
    @GET("/api/colormaps/colors/{colorId}/posts")
    suspend fun getColorPosts(
        @Path("colorId") colorId: Long,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10
    ): Response<ColorPostsResponse>

    // 포스트 삭제
    @DELETE("/api/colormaps/posts/{postId}")
    suspend fun deletePost(
        @Path("postId") postId: Long
    ): Response<Unit>

    // 특정 색상의 특정 포스트 상세 조회
    @GET("/api/colormaps/colors/{colorId}/posts/{postId}")
    suspend fun getPostDetail(
        @Path("colorId") colorId: Long,
        @Path("postId") postId: Long
    ): Response<PostDetailResponse>

    // 게시물의 대표 색상 수정
    @PATCH("/api/colormaps/posts/{postId}/mainColor")
    suspend fun updatePostColor(
        @Path("postId") postId: Long,
        @Body request: Map<String, Long> // {"newColorId": 0}
    ): Response<ColorUpdateResponse>
}