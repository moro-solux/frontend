package com.solux.moro.data.mapper

import androidx.compose.ui.graphics.Color
import com.solux.moro.data.dto.UserProfileDto
import com.solux.moro.data.model.User
import com.solux.moro.data.model.UserColorPalette
import com.solux.moro.data.model.UserStats

fun UserProfileDto.toDomain(): User {
    return User(
        id = this.userId,
        nickname = this.userName,
        colorPalette = UserColorPalette(
            userColor =  ColorMapper.toColorFromHex(this.userColorHex) ,
            paletteColors = this.colorCodes.map { colorCode ->
                ColorMapper.toColorFromId(colorCode.colorId) ?: Color.Transparent
            } as List<Color>
        ),
        userColorHex = this.userColorHex,
        visible = this.visible
    )
}

fun UserProfileDto.toStatsDomain(): UserStats {
    return UserStats(
        colorsCount = this.colorCount,
        followerCount = this.followerCount,
        followingCount = this.followingCount,
        isFollowing = this.followingStatus == "ACCEPTED"||this.followingStatus=="PENDING",
    )
}

