package com.solux.moro.ui.notification

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.solux.moro.R
import com.solux.moro.components.BackNavigationTopAppBar
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.theme.MoroTheme
import com.solux.moro.data.model.NotificationUiModel
import com.solux.moro.ui.notification.component.Notification

@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel= hiltViewModel(),
    navController: NavHostController,
    color: Color = Color(0xFFA3A3A3),
    style: TextStyle = MoroTheme.typography.bodyRegular14
) {

    Log.d(
        "VM_UI",
        "NotificationScreen recomposed - VM hash=${viewModel.hashCode()}"
    )
    val navController= navController
    val notifications by viewModel.notifications.collectAsStateWithLifecycle()
    //val notificationList by viewModel.notificationList.collectAsStateWithLifecycle()
    val visible=viewModel.visible
    Scaffold(
        topBar = { BackNavigationTopAppBar("알림",{
            navController.popBackStack()
        }) },
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
            if(visible)
                FollowNavigationItem( navController = navController)
            //val notifications by viewModel.notifications.collectAsState(initial = emptyMap())

            Box(modifier = Modifier.weight(1f)) {
                NotificationList(
                    navController = navController,
                    groupedData = notifications,
                    visible=visible,
                    onItemClick = { notificationId ->
                        viewModel.onNotificationClick(notificationId)
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
    visible:Boolean,
    onItemClick: (Long) -> Unit) {
    Log.d("UI_RECOMPOSE", "현재 맵 섹션 개수: ${groupedData.size}")
    LazyColumn (
        modifier=Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        item {
            val topSpace = if (visible) 100.dp else 0.dp
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(topSpace)
            )
        }
        groupedData.forEach {(header, notifications) ->
            item {
                Text(
                    text = header,
                    modifier = Modifier.fillMaxWidth().padding(start=12.dp),
                    style = MoroTheme.typography.bodyRegular14,
                    color = Color.White
                )
            }
            items(notifications,
                key = { it.id }) { notification ->
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
fun FollowNavigationItem(navController: NavHostController){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clickable {
            navController.navigate("follow_request")},
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


