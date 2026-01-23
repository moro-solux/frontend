package com.solux.moro.data.service

import com.solux.moro.data.dto.AuthResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    @FormUrlEncoded
    @POST("/api/auth/complete-registration")
    suspend fun completeRegistration(
        @Field("email") email: String,
        @Field("userName") nickname: String,
        @Field("sensitivity") sensitivity: Int
    ): Response<AuthResponse>

    @GET("/api/auth/check-nickname")
    suspend fun checkNickname(
        @Query("userName") userName: String
    ): Response<ResponseBody>
}
