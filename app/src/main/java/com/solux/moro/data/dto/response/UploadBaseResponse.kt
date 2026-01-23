package com.solux.moro.data.dto.response

import com.google.gson.annotations.SerializedName


data class UploadBaseResponse<T>(
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: T?
)