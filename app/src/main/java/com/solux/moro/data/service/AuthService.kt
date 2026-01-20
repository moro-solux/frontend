package com.solux.moro.data.service

import com.solux.moro.data.dto.AuthResponse
import com.solux.moro.data.dto.CompleteRegistrationRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/api/auth/complete-registration")
    suspend fun completeRegistration(
        @Body request: CompleteRegistrationRequest
    ): Response<AuthResponse>
}
