package com.solux.moro.data.repository.colormap

import com.solux.moro.data.dto.response.ColorMapListResponse
import com.solux.moro.data.dto.response.ColorPostsResponse
import com.solux.moro.data.dto.response.ColorUpdateResponse
import com.solux.moro.data.dto.response.PostDetailResponse
import com.solux.moro.data.dto.response.SingleThemeResponse
import com.solux.moro.data.repository.ColorMapRepository
import com.solux.moro.data.service.ColorMapService
import retrofit2.Response
import javax.inject.Inject

class ColorMapRepositoryImpl @Inject constructor(
    private val service: ColorMapService
) : ColorMapRepository {

    override suspend fun getAllColorMaps(): Response<ColorMapListResponse> {
        return service.getAllColorMaps()
    }

    override suspend fun getThemeDetails(themeName: String): Response<SingleThemeResponse> {
        return service.getThemeDetails(themeName)
    }

    override suspend fun getColorPosts(colorId: Long, page: Int, size: Int): Response<ColorPostsResponse> {
        return service.getColorPosts(colorId, page, size)
    }

    override suspend fun deletePost(postId: Long): Response<Unit> {
        return service.deletePost(postId)
    }

    override suspend fun getPostDetail(colorId: Long, postId: Long): Response<PostDetailResponse> {
        return service.getPostDetail(colorId, postId)
    }

    override suspend fun updatePostColor(postId: Long, request: Map<String, Long>): Response<ColorUpdateResponse> {
        return service.updatePostColor(postId, request)
    }
}