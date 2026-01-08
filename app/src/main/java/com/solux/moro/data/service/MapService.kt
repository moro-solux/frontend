package com.solux.moro.data.service

import retrofit2.http.GET
import retrofit2.http.Path
import com.solux.moro.data.dto.response.MapPostDetailDto
import com.solux.moro.data.dto.response.MapPostDto
import retrofit2.http.Query

interface MapService {
    @GET("/api/map")
    suspend fun getNearbyPosts(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("radius") radius: Double,
    ): List<MapPostDto>

    @GET("/api/map/search")
    suspend fun searchPosts(
        @Query("keyword") keyword: String,
        @Query("radius") radius: Double,
    ): List<MapPostDto>

    @GET("/api/map/{postId}")
    suspend fun getPostDetail(
        @Path("postId") postId: Long,
    ): MapPostDetailDto
}