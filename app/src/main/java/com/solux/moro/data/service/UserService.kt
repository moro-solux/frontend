package com.solux.moro.data.service

import com.solux.moro.data.dto.BaseResponse
import com.solux.moro.data.dto.ColorThemeDto
import com.solux.moro.data.dto.MainColorEditRequest
import com.solux.moro.data.dto.UserProfileDto
import com.solux.moro.data.dto.UserProfileEditRequest
import com.solux.moro.data.dto.UserSearchResponseDto
import com.solux.moro.data.model.FollowUserResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @PUT("/api/users/me/profile") //프로필 수정 (이름, 프로필 배경색
    suspend fun profileEdit(
        @Body profileRequest: UserProfileEditRequest
    ): BaseResponse<String>

    @PUT("/api/users/me/colors/main") //유저 컬러 팔레트 수정
    suspend fun mainColorEdit(
        @Body colorRequest: MainColorEditRequest
    ): BaseResponse<String>

    @GET("/api/colormaps")
    suspend fun getColorUnlockInfo():BaseResponse<List<ColorThemeDto>>

    @GET("/api/users/{userId}/profile")
    suspend fun getUserProfile(
        @Path("userId") userId: Long
    ): BaseResponse<UserProfileDto>

//    @GET("/api/users/{userId}/profile/feed")
//    suspend fun getUserProfileFeed(
//        @Path("userId") userId: Long
//    ): BaseResponse<List<FeedDto>>
//
    @GET("/api/users/{userId}/followings")
    suspend fun getFollowings(
        @Path("userId") userId: Long
    ): BaseResponse<FollowUserResponse>

    @GET("/api/users/{userId}/followers")
    suspend fun getFollowers(
        @Path("userId") userId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("keyword") keyword: String,
    ): BaseResponse<FollowUserResponse>

    @GET("/api/users/search")
    suspend fun searchUser(
        @Query("nickname") nickname: String
    ): BaseResponse<UserSearchResponseDto>

    @GET("/api/users/me/follow-requests")
    suspend fun getFollowRequests(): BaseResponse<FollowUserResponse>

    @DELETE("/api/users/me/followers/{targetUserId}") //팔로워 삭제
    suspend fun deleteFollower(
        @Path("targetUserId") targetUserId: Long
    ): BaseResponse<Unit>

}