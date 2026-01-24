package com.solux.moro.data.dto.request

// 댓글 작성용
data class CommentRequest(
    val misPostId: Long,
    val misContent: String
)

// 댓글 수정용
data class CommentEditRequest(
    val newContent: String
)


data class MissionUploadRequest(
    val content: String,
    val missionId: Long,
    val score: Int
)
