package com.solux.moro.data.repository

import com.solux.moro.data.dto.response.ColorMapListResponse
import com.solux.moro.data.dto.response.ColorPostsResponse
import com.solux.moro.data.dto.response.ColorUpdateResponse
import com.solux.moro.data.dto.response.PostDetailResponse
import com.solux.moro.data.dto.response.SingleThemeResponse
import retrofit2.Response

interface ColorMapRepository {
    // 전체 컬러맵 조회
    suspend fun getAllColorMaps(): Response<ColorMapListResponse>

    // 테마 상세 조회
    suspend fun getThemeDetails(themeName: String): Response<SingleThemeResponse>

    suspend fun getColorPosts(colorId: Long, page: Int, size: Int): Response<ColorPostsResponse>

    // 포스트 삭제
    suspend fun deletePost(postId: Long): Response<Unit>

    // 게시물 상세 조회
    suspend fun getPostDetail(colorId: Long, postId: Long): Response<PostDetailResponse>

    // 게시물 대표 색상 수정
    suspend fun updatePostColor(postId: Long, request: Map<String, Long>): Response<ColorUpdateResponse>
}