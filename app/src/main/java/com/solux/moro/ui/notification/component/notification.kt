package com.solux.moro.ui.notification.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.solux.moro.R
import com.solux.moro.core.designsystem.theme.MoroTheme
import com.solux.moro.data.model.NotificationUiModel

@Composable
fun Notification(
    navController: NavHostController,
    notification: NotificationUiModel,
    color: Color = MoroTheme.colors.fontColor,
    style: TextStyle = MoroTheme.typography.bodyRegular16,
    onFollowClick: (Long) -> Unit = {},
    unFollowClick: (Long) -> Unit = {},
    onReadClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .border(width = 1.dp,
                color = Color(0xFF404040),
                shape = RoundedCornerShape(size = 8.dp))
            .width(360.dp)
            .background(color = Color(0xFF262626), shape = RoundedCornerShape(size = 8.dp))
            .padding(13.dp)
            .clickable(
                onClick = {
                    onReadClick()
                    when (notification) {
                        is NotificationUiModel.Comment -> {
                            //navController.navigate(notification.) 해당 게시물로 이동
                        }
                        is NotificationUiModel.Liked -> {
                            //navController.navigate()  해당 게시물로 이동
                        }
                        is NotificationUiModel.Following -> {
                            //
                        }
                        is NotificationUiModel.ColorUnlocked -> {
                            //navController.navigate("colorMap")
                        }
                        is NotificationUiModel.Mission -> {
                            //navController.navigate("mission")
                        }
                    }

                }
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier
            .weight(1f, fill = false)
            .padding(top=5.dp)
        ) {
            Column (
                //Modifier.padding(top=5.dp)
            ){
            Image(
                painter = painterResource(id = notification.type.iconRes()),
                contentDescription = "image description",
                modifier = Modifier
                    .size(40.dp))
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(1f, fill = false)
            ) {
                val text = when (notification) {  //제목 텍스트
                    is NotificationUiModel.Comment -> {
                        stringResource(notification.type.messageRes(), notification.userName)
                    }
                    is NotificationUiModel.Liked -> {
                        val othersCount = notification.totalCount - 1
                        if (othersCount > 0) {
                            stringResource(R.string.notification_like_message_n, notification.userName, othersCount)
                        } else {
                            stringResource(R.string.notification_like_message, notification.userName)
                        }

                    }
                    is NotificationUiModel.Following -> {
                        stringResource(notification.type.messageRes(), notification.userName)
                    }
                    is NotificationUiModel.ColorUnlocked -> {
                        stringResource(notification.type.messageRes())
                    }
                    is NotificationUiModel.Mission -> {
                        stringResource(notification.type.messageRes(), notification.content)
                    }

                }
                //Log.d("Notification", "Notification: $notification")

                Text(
                    text = text,
                    color = color,
                    style = style,
                )

                val contentText = when (notification) { //본문 텍스트
                    is NotificationUiModel.Comment -> stringResource(notification.type.contentRes(),notification.content)
                    is NotificationUiModel.Mission -> stringResource(notification.type.contentRes(), notification.content) //시간 계산..???
                    is NotificationUiModel.ColorUnlocked -> stringResource(notification.type.contentRes(),) //notification.content)
                    else -> ""
                }

                if (contentText.isNotEmpty()) {
                    Row(modifier = Modifier.padding(top = 5.dp).width(200.dp)) {
                        Text(
                            text = contentText,
                            color = Color(0xFFA3A3A3),
                            style = MoroTheme.typography.bodyRegular14,
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                ) {
                    Text(
                        text = notification.createdAt,
                        color = Color(0xFF737373),
                        style = MoroTheme.typography.bodyRegular12,
                    )
                    Spacer(Modifier.width(18.dp))
                }
            }
        }
        NotificationSide(notification,
            onFollowClick = onFollowClick,
            unFollowClick = unFollowClick
        )
    }
}
@Composable
fun NotificationSide(notification: NotificationUiModel,
                     onFollowClick: (Long) -> Unit = {},
                     unFollowClick: (Long) -> Unit = {}, ){
    Column(
        modifier = Modifier.padding(end = 10.dp, top = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        when (notification) {
            is NotificationUiModel.Comment -> { //읽음 표시
                ReadIcon(notification.isRead)
            }
            is NotificationUiModel.Mission -> { //읽음 표시
                ReadIcon(notification.isRead)
            }
            is NotificationUiModel.ColorUnlocked -> {//읽음 표시
                ReadIcon(notification.isRead)
            }
            is NotificationUiModel.Liked -> { //사진
                PhotoImage(notification.imageUrl)
            }
            is NotificationUiModel.Following -> { //버튼
                Column (modifier= Modifier){
                    Log.d("NotificationSide", "notification.isFollowing: ${notification.isFollowing}")
                    var btnColor=Color.White
                    var textColor=Color.Black
                    Button(
                        onClick = {
                            if(notification.isFollowing) {
                                unFollowClick(notification.userId)
                                btnColor=Color.Black
                                textColor=Color.White
                            }
                            else
                                onFollowClick(notification.userId)
                        },
                        modifier = Modifier
                            .width(70.dp)
                            .height(25.dp),
                        shape = ButtonDefaults.shape,
                        colors = ButtonDefaults.buttonColors(
                            btnColor,
                            textColor
                        ),
                        contentPadding = PaddingValues(all = 0.dp),
                    ) {
                        Text(
                            text= if(notification.isFollowing)
                                "following"
                            else
                                "follow",
                            color = textColor,
                            style = MoroTheme.typography.bodyRegular12,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ReadIcon(read: Boolean){
    Column(
        modifier = Modifier
            .size(21.dp)
            .padding(7.dp)
            .background(
                shape = RoundedCornerShape(size = 8.dp),
                color = if (!read) Color(0xFF737373) else Color.Transparent,
            ),
    ) {}
}
@Composable
fun PhotoImage(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .build(),
        placeholder = painterResource(R.drawable.img_feed),
        error = painterResource(R.drawable.img_feed),
        fallback = painterResource(R.drawable.img_feed),
        contentDescription = null,
        modifier = modifier
            .size(50.dp),
        contentScale = ContentScale.Crop,
    )
}

