package com.solux.moro.data.service

import com.solux.moro.data.dto.BaseResponse
import com.solux.moro.data.dto.FollowRequestDto
import com.solux.moro.data.dto.MainColorEditRequest
import com.solux.moro.data.dto.UserProfileDto
import com.solux.moro.data.dto.UserProfileEditRequest
import com.solux.moro.data.dto.UserSearchResponseDto
import com.solux.moro.ui.followlist.FollowUserInfo
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @PUT("api/users/me/profile") //프로필 수정 (이름, 프로필 배경색
    suspend fun profileEdit(
        @Body profileRequest: UserProfileEditRequest
    ): BaseResponse<String>

    @PUT("api/users/me/colors/main") //유저 컬러 팔레트 수정
    suspend fun mainColorEdit(
        @Body colorRequest: MainColorEditRequest
    ): BaseResponse<Unit>

    @GET("api/users/{userId}/profile")
    suspend fun getUserProfile(
        @Path("userId") userId: Long
    ): BaseResponse<UserProfileDto>

//    @GET("api/users/{userId}/profile/feed")
//    suspend fun getUserProfileFeed(
//        @Path("userId") userId: Long
//    ): BaseResponse<List<FeedDto>>
//
    @GET("api/users/{userId}/followings")
    suspend fun getFollowings(
        @Path("userId") userId: Long
    ): BaseResponse<List<FollowUserInfo>>

    @GET("api/users/{userId}/followers")
    suspend fun getFollowers(
        @Path("userId") userId: Long
    ): BaseResponse<List<FollowUserInfo>>

    @GET("api/users/search")
    suspend fun searchUser(
        @Query("nickname") nickname: String
    ): BaseResponse<UserSearchResponseDto>

    @GET("api/users/me/follow-requests")
    suspend fun getFollowRequests(): BaseResponse<List<FollowRequestDto>>

    @DELETE("api/users/me/followers/{targetUserId}")
    suspend fun deleteFollower(
        @Path("targetUserId") targetUserId: Long
    ): BaseResponse<Unit>
}