package com.solux.moro.ui.profile.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.solux.moro.core.designsystem.component.PIXEL_DENSITY
import com.solux.moro.core.designsystem.theme.MoroTheme
import com.solux.moro.ui.profile.ProfileAction

val Number.toPxDp: Dp get() = (this.toDouble()/ PIXEL_DENSITY).dp
val Number.toPxSp: TextUnit get() = (this.toDouble() / PIXEL_DENSITY).sp

@Composable
fun Profile(
    nickname: String,
    userColorHex: String?,

    colorsCnt: Int,
    followerCnt: Int,
    followingCnt: Int,
    action: ProfileAction,
    navController: NavController,
    isFollowing: Boolean,
    onEditProfile: () -> Unit,
    onFollow: () -> Unit,
    unFollow: () -> Unit,
) {
    val colorsCnt: Int = colorsCnt
    val followerCnt: Int = followerCnt
    val followingCnt: Int = followingCnt

    Column(
        Modifier
            .fillMaxWidth()
            .padding(
                start = 46.08.toPxDp,
                top = 46.08.toPxDp,
                end = 46.08.toPxDp,
                bottom = 46.08.toPxDp
            ),
        verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ProfileHeader(
            nickname,
            userColorHex,
        )

        Column(
            Modifier
                .width(987.84003.toPxDp),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ProfileActionButton(
                isFollowing=isFollowing,
                action = action,
                onEditProfile = onEditProfile,
                onFollow = onFollow,
                unFollow = unFollow
            )
            ProfileInfo(
                colorsCnt,
                followerCnt,
                followingCnt,
                action = action,
                navController = navController
            )
        }
    }
}


@Composable
fun ProfileInfo(
    user_colors_cnt: Int = 124,
    user_photos_cnt: Int = 1200,
    user_following_cnt: Int = 89,
    action: ProfileAction,
    navController: NavController
){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .width(688.77002.toPxDp)
            .height(115.2.toPxDp)
    ) {
        ProfileStatItem(user_colors_cnt,
            "Colors",
            onClick = {}
            )
        ProfileStatItem(user_photos_cnt,
            "Follower",
            onClick = {
                when (action) {
                    ProfileAction.EditProfile -> navController.navigate("follow")
                    ProfileAction.Follow -> {}
                }
            }
        )
        ProfileStatItem(user_following_cnt,
            "Following",
            onClick = {
                when (action) {
                    ProfileAction.EditProfile -> navController.navigate("follow")
                    ProfileAction.Follow -> {}
                }
            }
        )
    }
}

@Composable
fun ProfileActionButton(
    isFollowing: Boolean,
    action: ProfileAction,
    onEditProfile: () -> Unit,
    onFollow: () -> Unit,
    unFollow:() ->Unit,

) {
    Button(
        onClick = {
            when (action) {
                ProfileAction.EditProfile -> onEditProfile()
                ProfileAction.Follow -> {
                    if(isFollowing) unFollow() //이미 팔로우 중인 경우 클릭시 언팔
                    else onFollow()//팔로우 하지 않는 경우 클릭시 팔로우
                }
            }
        },
        shape = RoundedCornerShape(size = 46.08.toPxDp),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .border(width = 2.88.toPxDp, color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 46.08.toPxDp))
            .width(374.39999.toPxDp)
            .height(30.dp)
    ) {
        Text(
            text = when (action) {
                ProfileAction.EditProfile -> "프로필 편집"
                ProfileAction.Follow -> {
                    if(isFollowing) "팔로잉" //이미 팔로우 중인 경우
                    else "팔로우" //팔로우 하지 않는 경우
                }
            },
            style = MoroTheme.typography.bodyRegular14,
        )
    }
}

@Composable
fun ProfileStatItem(cnt: Int, title: String,onClick: () -> Unit){
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(183.50999.toPxDp)
            .clickable{
                onClick()
            }
    ) {
        cnt.toDouble()
        Text(
            text= when {
                cnt < 1000 -> cnt.toString()
                cnt < 1000000 -> {
                    val k = cnt / 1000f
                    if (k >= 100) "${k.toInt()}K" // 100K 이상은 소수점 생략
                    else String.format("%.1fK", k).replace(".0K", "K")
                }

                else -> {
                    val m = cnt / 1000000f
                    if (m >= 100) "${m.toInt()}M"
                    else String.format("%.1fM", m).replace(".0", "")
                }
            },
            style = TextStyle(
                fontSize = 40.32.toPxSp,
                lineHeight = 56.45.toPxSp,
                fontWeight = FontWeight(400),
                color = Color(0xFFF2F2F2),
                textAlign = TextAlign.Center,
            ),
        )
        Text(
            text = title,
            style = MoroTheme.typography.bodyRegular14,
            color = MoroTheme.colors.fontColor
        )
    }
}
//
//@Preview(device = Devices.PIXEL_4A)
//@Composable
//fun ProfileActionButtonPreview(){
//    ProfileActionButton(
//        action = ProfileAction.EditProfile,
//        onEditProfile = {},
//        onFollow = {}
//    )
//}
