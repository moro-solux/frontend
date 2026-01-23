package com.solux.moro.data.model

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName
import com.solux.moro.data.dto.TopLikerDto


data class FeedItem(
    val id: Long,
    val authorId: Long,
    val authorNickname: String,
    //val authorProfileColor: Color,
    val hexCodes: List<String>,
    val contentColors: List<Color>,
    val imageUrl: String?,
    val commentCount: Int,
    val likeCount: Int,
    val isLiked: Boolean,
    val createdAt: String
)

data class ProfileFeedItem(
    val id: Long,
    val imageUrl: String?,
)

data class PostDto(
    @SerializedName("id") val id: Long,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("userId") val userId: Long,
    @SerializedName("userName") val userName: String,
    @SerializedName("userProfileImageUrl") val userProfileImageUrl: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("likecount") val likeCount: String,
    @SerializedName("shareCount") val shareCount: Int,
    @SerializedName("commentCount") val commentCount: Int,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,
    @SerializedName("placeName") val placeName: String,
    @SerializedName("colors") val colors: List<ColorInfo>
)//개별 게시물 조회
data class ColorInfo(
    @SerializedName("colorId") val colorId: Int,
    @SerializedName("hexCode") val hexCode: String,
    @SerializedName("ratio") val ratio: Double
)
data class FeedData(
    @SerializedName("content") val content: List<PostDto>,
    @SerializedName("currentPage") val currentPage: Int,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("hasNext") val hasNext: Boolean
) //게시물 목록 조회

data class LikeDto(
    @SerializedName("totalCount") val totalCount: Int,
    @SerializedName("topLiker") val topLiker: TopLikerDto,
)

data class CommentDto(
    @SerializedName("id") val id: Long,
    @SerializedName("content") val content: String,
    @SerializedName("username") val username: String,
    @SerializedName("createdAt") val createdAt: String,
)

data class CommentRequest(
    val content: String
)
data class UserProfileData(
    val viewType: String,
    val page: PageData
)

data class PageData(
    val content: List<ProfilePostDto>,
    val currentPage: Int,
    val totalPages: Int,
    val hasNext: Boolean
)
data class ProfilePostDto(
    val postId: Long,
    val imageUrl: String
)