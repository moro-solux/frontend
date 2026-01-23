package com.solux.moro.ui.followlist.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.solux.moro.R
import com.solux.moro.core.designsystem.theme.MoroTheme
import com.solux.moro.data.model.UserInfo
import com.solux.moro.ui.profile.component.toPxDp
import com.solux.moro.ui.profile.component.toPxSp

@Composable
fun FollowRequestUserItem(
    user: UserInfo,
    onAcceptClick: () -> Unit,
    onDeclineClick: () -> Unit
) {

    Column() {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(987.84003.toPxDp)
                .height(207.36.toPxDp)
                .padding(top = 34.56.toPxDp, bottom = 34.56.toPxDp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    23.040000915527344.toPxDp,
                    Alignment.Start
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_user_profile),
                    contentDescription = "image description",
                    contentScale = ContentScale.None
                )
                Text(
                    text = user.userName,
                    style = TextStyle(
                        fontSize = 46.08.toPxSp,
                        lineHeight = 64.51.toPxSp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFF2F2F2),
                    ),
                    modifier = Modifier
                        .width(324.toPxDp)
                        .height(65.toPxDp)
                )
            }
                FollowRequestButton(onAcceptClick, onDeclineClick)
            }
        }
    HorizontalDivider(thickness = 1.dp, color = Color(0xFF262626))
}

@Composable
private fun FollowRequestButton(
    onAcceptClick:  () -> Unit,
    onDeclineClick: () -> Unit
){
    var containerColor = Color(0xFF262626)
    var contentColor = Color(0xFFF2F2F2)
    var borderColor=Color(0xFF404040)
    var text="확인"
Row(
    horizontalArrangement = Arrangement.spacedBy(
        10.dp)
) {
    Button(
        onClick = onAcceptClick,
        modifier = Modifier
            .width(55.dp)
            .height(35.dp), colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF2F2F2),
            contentColor = Color(0xFF121212),
        ), shape = RoundedCornerShape(size = 23.04.toPxDp),
        contentPadding = PaddingValues(
            horizontal = 0.dp,
            vertical = 0.dp
        ), border = BorderStroke(width = 2.88.toPxDp, color = Color(0xFF262626))
    ) {
        Text(
            text = "확인",
            style = MoroTheme.typography.bodyRegular16
        )
    }

    Button(
        onClick = onDeclineClick,
        modifier = Modifier
            .width(55.dp)
            .height(35.dp), colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF262626),
            contentColor = Color(0xFFF2F2F2),
        ), shape = RoundedCornerShape(size = 23.04.toPxDp),
        contentPadding = PaddingValues(
            horizontal = 0.dp,
            vertical = 0.dp
        ), border = BorderStroke(width = 2.88.toPxDp, color = Color(0xFF404040))
    ) {
        Text(
            text = "삭제",
            style = MoroTheme.typography.bodyRegular16
        )
    }
}
}

//@Preview( device = Devices.PIXEL_4A)
//@Composable
//fun FollowRequestUserItemPreview(){
//    FollowRequestUserItem(
//        user = User(
//            id = 1,
//            email = "test@test.com",
//            nickname = "테스트유저",
//            colorPalette = UserColorPalette(
//                theme = MoroThemeType.Pastel,
//                userColor = MoroPalette.Pastel.Purple400,
//                paletteColors = listOf(
//                    MoroPalette.Pastel.Purple400,
//                    MoroPalette.Pastel.Yellow300,
//                    MoroPalette.Pastel.Green200,
//                    MoroPalette.Pastel.Cyan200,
//                    MoroPalette.Pastel.Indigo500,
//                    MoroPalette.Pastel.Gray400
//                )
//            )
//        ),
//        stats = UserStats(
//            colorsCount = 1,
//            followerCount = 1,
//            followingCount = 1,
//            isFollowing = true
//        ),
//        onAcceptClick = {},
//        onDeclineClick = {}
//    )
//}
//
