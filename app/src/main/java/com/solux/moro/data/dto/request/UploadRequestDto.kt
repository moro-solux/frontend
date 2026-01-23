package com.solux.moro.data.dto.request

import com.google.gson.annotations.SerializedName

// 캡처 요청
data class CaptureRequestDto(
    @SerializedName("image") val image: String
)

// 위치 수정 요청
data class LocationRequestDto(
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lng") val longitude: Double,
    @SerializedName("placeName") val placeName: String
)

// 색상 수정 요청
data class ColorRequestDto(
    @SerializedName("selectedColorId") val selectedColorId: Int
)

// 캡처 결과 응답
data class CaptureResultDto(
    @SerializedName("draftId") val draftId: Long,
    @SerializedName("top4Colors") val top4Colors: List<ColorItemDto>,
    @SerializedName("autoSelectedMainColor") val autoSelectedMainColor: Int
)

// 색상 아이템 정보
data class ColorItemDto(
    @SerializedName("colorId") val colorId: Int,
    @SerializedName("hexCode") val hexCode: String,
    @SerializedName("ratio") val ratio: Double
)