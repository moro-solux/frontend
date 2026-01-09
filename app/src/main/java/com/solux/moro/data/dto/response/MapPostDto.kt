package com.solux.moro.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MapPostDto(
    @SerialName("postId")
    val postId: Long,
    @SerialName("lat")
    val lat: Double,
    @SerialName("lng")
    val lng: Double,
    @SerialName("title")
    val title: String? = null,
    @SerialName("thumbnailUrl")
    val thumbnailUrl: String? = null,
)

@Serializable
data class MapPostDetailDto(
    @SerialName("postId")
    val postId: Long,
    @SerialName("title")
    val title: String,
    @SerialName("address")
    val address: String,
    @SerialName("date")
    val date: String,
    @SerialName("colors")
    val colors: List<String>,
    @SerialName("imageUrl")
    val imageUrl: String? = null,
)
