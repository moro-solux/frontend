package com.solux.moro.ui.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.solux.moro.R
import com.solux.moro.components.BackNavigationTopAppBar
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.theme.MoroTheme
import com.solux.moro.core.navigation.Profile
import com.solux.moro.data.model.NotificationUiModel
import com.solux.moro.ui.notification.component.Notification
import kotlinx.coroutines.flow.StateFlow

@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel= hiltViewModel(),
    navController: NavHostController,
    color: Color = Color(0xFFA3A3A3),
    style: TextStyle = MoroTheme.typography.bodyRegular14
) {

    val navController= navController
    Scaffold(
        topBar = { BackNavigationTopAppBar("알림",{}) },
        bottomBar = { BottomBar() }
    ) {innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .background(color = Color(0xFF121212))
                .padding(top=10.dp)
                .padding(horizontal=10.dp)
                .padding(innerPadding,),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            FollowNavigationItem(userNickname = viewModel.nickname, navController = navController)
            val notifications by viewModel.notifications.collectAsState(initial = emptyMap())
            val mock  = mapOf(
                "Today" to listOf(
                    NotificationUiModel.Comment(
                        id = 1L,
                        userName = "김철수",
                        postId = 101,
                        content = "와! 이 사진 정말 잘 나왔네요. 어디서 찍으신 건가요?",
                        createdAt = "오전 01:54",
                        isRead = false
                    ),
                    NotificationUiModel.ColorUnlocked(
                        id = 3L,
                        createdAt = "오후 10:30",
                        isRead = false
                    ),
                    NotificationUiModel.Liked(
                        id = 2L,
                        userName = "이영희",
                        postId = 102,
                        imageUrl = null,
                        createdAt = "오전 01:20",
                        isRead = false
                    ),
                    NotificationUiModel.Mission(
                    id = 5L,
                    content = "새로운 주간 미션: 사진 3장 업로드하기",
                    createdAt = "1월 7일",
                    isRead = false
                )
                ),
                "Yesterday" to listOf(
                    NotificationUiModel.ColorUnlocked(
                        id = 3L,
                        createdAt = "오후 10:30",
                        isRead = false
                    ),
                    NotificationUiModel.Following(
                        id = 4L,
                        userName = "박지성",
                        createdAt = "오후 06:15",
                        isRead = false
                    )
                ),
                "Last 7 days" to listOf(
                    NotificationUiModel.Mission(
                        id = 5L,
                        content = "새로운 주간 미션: 사진 3장 업로드하기",
                        createdAt = "1월 7일",
                        isRead = true
                    )
                ),
                "Earlier" to listOf(
                    NotificationUiModel.Mission(
                        id = 5L,
                        content = "새로운 주간 미션: 사진 3장 업로드하기",
                        createdAt = "1월 7일",
                        isRead = true
                    )
                )
            )
            val notificationList = mock
            Box(modifier = Modifier.weight(1f)) {
                NotificationList(
                    navController = navController,
                    groupedData = notifications,
                    onItemClick = { notificationId ->
                        //viewModel.onNotificationClick(notificationId)
                    }
                )
            }
        }
    }
}

@Composable
fun NotificationList(
    navController: NavHostController,
    groupedData: Map<String, List<NotificationUiModel>>,
    onItemClick: (Long) -> Unit) {
    LazyColumn (
        modifier=Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        groupedData.forEach {(header, notifications) ->
            item {
                Text(
                    text = header,
                    modifier = Modifier.fillMaxWidth().padding(start=12.dp),
                    style = MoroTheme.typography.bodyRegular14,
                    color = Color.White
                )
            }
            items(notifications) { notification ->
                Notification(
                    navController,
                    notification,
                    onReadClick = { onItemClick(notification.id) })
            }
            item{
                Spacer(modifier=Modifier.height(5.dp))
            }
        }
    }
}


@Composable
fun FollowNavigationItem(navController: NavHostController, userNickname: StateFlow<String>){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clickable {
            navController.navigate(Profile.createRoute(userNickname.value))},
        horizontalArrangement = Arrangement.spacedBy(10.dp,Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        ){
        Image(
            painter = painterResource(id = R.drawable.icon_follow_navigation),
            contentDescription = "image description",
            //tint=Color.White,
            modifier = Modifier
                .size(45.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp,Alignment.Top)){
            Text("팔로우 요청",
                style = MoroTheme.typography.bodyBold14,
                color=Color.White)
            Text("요청을 확인하고 승인 혹은 거절 하세요.",
                style = MoroTheme.typography.bodyRegular12,
                color=Color.White)
        }
    }

}


