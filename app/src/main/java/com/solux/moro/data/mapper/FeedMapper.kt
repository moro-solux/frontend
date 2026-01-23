package com.solux.moro.data.mapper

import android.util.Log
import com.solux.moro.data.model.CommentDto
import com.solux.moro.data.model.CommentItem
import com.solux.moro.data.model.FeedItem
import com.solux.moro.data.model.PostDto
import com.solux.moro.data.model.ProfileFeedItem
import com.solux.moro.data.model.ProfilePostDto
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

fun PostDto.toDomain(): FeedItem {
    val hexList = this.colors.map { it.hexCode }
    val composeColors = hexList.map { hex ->
        ColorMapper.toColorFromHex(hex)
    }

    return FeedItem(
        id = this.id,
        authorId = this.userId,
        authorNickname = this.userName,
        //authorProfileColor =,
        hexCodes = hexList,
        contentColors = composeColors,
        imageUrl = this.imageUrl,
        commentCount = this.commentCount,
        likeCount = this.likeCount.toIntOrNull() ?: 0,
        isLiked = false,
        createdAt = this.createdAt.toRelativeTime()
    )
}

fun ProfilePostDto.toDomain(): ProfileFeedItem {
    return ProfileFeedItem(
        id = this.postId,
        imageUrl = this.imageUrl
    )
}


fun CommentDto.toDomain(): CommentItem {
    return CommentItem(
        id = this.id,
        userNickname = this.username,
        content = this.content,
        createdAt = this.createdAt.toRelativeTime()
    )
}


fun String?.toRelativeTime(): String {
    if (this.isNullOrBlank()) return ""

    return try {
        val postTime = if (this.endsWith("Z")) {
            java.time.ZonedDateTime.parse(this).withZoneSameInstant(java.time.ZoneId.systemDefault()).toLocalDateTime()
        } else {
            LocalDateTime.parse(this)
        }

        val now =LocalDateTime.now(ZoneId.of("Asia/Seoul"))


        Log.d("toRelativeTime", "postTime: $postTime")
        Log.d("toRelativeTime", "now: $now")

        val seconds = ChronoUnit.SECONDS.between(postTime, now)
        val minutes = ChronoUnit.MINUTES.between(postTime, now)
        val hours = ChronoUnit.HOURS.between(postTime, now)
        val days = ChronoUnit.DAYS.between(postTime, now)

        when {
            seconds < 60 -> "방금 전"
            minutes < 60 -> "${minutes}분 전"
            hours < 24 -> "${hours}시간 전"
            days < 7 -> "${days}일 전"
            else -> {
                val formatter = DateTimeFormatter.ofPattern("yy.MM.dd", Locale.getDefault())
                postTime.format(formatter)
            }
        }
    } catch (e: Exception) {
        this ?: ""
    }
}