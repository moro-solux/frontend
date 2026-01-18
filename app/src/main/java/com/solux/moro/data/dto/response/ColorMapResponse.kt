package com.solux.moro.data.dto.response

// 전체 컬러맵 조회
data class ColorMapListResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<ThemeDetailDto>
)

data class ThemeDetailDto(
    val themeName: String,
    val colors: List<ColorInfoDto>
)

// 특정 색상의 포스트 리스트 조회
data class ColorPostsResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: ColorPostContentDto
)

data class ColorPostContentDto(
    val content: List<ColorPostDto>,
    val currentPage: Int,
    val totalPages: Int,
    val hasNext: Boolean
)

data class ColorPostDto(
    val postId: Long,
    val imageUrl: String
)

// 특정 테마 하나
data class SingleThemeResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: ThemeDetailDataDto
)

data class ThemeDetailDataDto(
    val themeName: String,
    val colors: List<ColorInfoDto>
)

data class ColorInfoDto(
    val colorId: Long,
    val hexCode: String,
    val postCount: Int,
    val unlocked: Boolean,
    val isRepresentative: Boolean
)


// 개별 게시물 상세 정보
data class PostDetailResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: PostDetailDataDto
)

data class PostDetailDataDto(
    val username: String,
    val postId: Long,
    val imageUrl: String,
    val mainColorId: Long,
    val colorCandidates: List<ColorCandidateDto>
)

data class ColorCandidateDto(
    val colorId: Long,
    val hexCode: String
)


//  색상 수정
data class ColorUpdateResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: UpdatedColorDto
)

data class UpdatedColorDto(
    val postId: Long,
    val updatedMainColorId: Long,
    val updatedHexCode: String,
    val isUnlocked: Boolean
)