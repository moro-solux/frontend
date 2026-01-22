package com.solux.moro.data.dto.response

import com.google.gson.annotations.SerializedName

data class MapPostDto(
    @SerializedName("postId")
    val postId: Long,
    @SerializedName(value = "lat", alternate = ["latitude"])
    val lat: Double,
    @SerializedName(value = "lng", alternate = ["longitude"])
    val lng: Double,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String? = null,
)

data class MapPostDetailDto(
    @SerializedName("postId")
    val postId: Long,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("placeName")
    val placeName: String,
    @SerializedName("addressKo")
    val addressKo: String,
    @SerializedName("addressEn")
    val addressEn: String,
    @SerializedName("hexCode1")
    val hexCode1: String,
    @SerializedName("hexCode2")
    val hexCode2: String,
    @SerializedName("hexCode3")
    val hexCode3: String,
    @SerializedName("hexCode4")
    val hexCode4: String,
    @SerializedName("imageUrl")
    val imageUrl: String? = null,
)

data class MapSearchResponseDto(
    @SerializedName(value = "centerLat", alternate = ["centerLatitude"])
    val centerLat: Double,
    @SerializedName(value = "centerLng", alternate = ["centerLongitude"])
    val centerLng: Double,
    @SerializedName("posts")
    val posts: List<MapPostDto>,
)
