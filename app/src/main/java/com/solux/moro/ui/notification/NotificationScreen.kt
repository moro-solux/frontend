package com.solux.moro.ui.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solux.moro.components.BackNavigationTopAppBar
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.theme.MoroTheme
import com.solux.moro.ui.notification.component.Notification
import com.solux.moro.ui.notification.component.NotificationType

@Composable
fun NotificationScreen(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFA3A3A3),
    style: TextStyle = MoroTheme.typography.bodyRegular14
) {
    Scaffold(
        topBar = { BackNavigationTopAppBar("알림",{}) },
        bottomBar = { BottomBar() }
    ) {innerPadding ->
        val scrollState = rememberScrollState()
        Column(
            Modifier
                .fillMaxSize()
                .background(color = Color(0xFF121212))
                .padding(top=10.dp)
                .padding(horizontal=10.dp)
                .padding(innerPadding,)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier=Modifier
                    .fillMaxWidth()
                    .padding(top=10.dp, start = 10.dp),
                horizontalArrangement = Arrangement.Start) {
                Text(
                    "Today",
                    color = color,
                    style = style
                )
            }
            Notification(
                type= NotificationType.COMMENT,
                name= "_sjwneooo",
                id= "@uzinnss",
                content="헐 잘 찍었따",)
            Notification(
                type= NotificationType.LIKE,
                name= "_sjwneooo",
                id= "@uzinnss",
                content=null,)
            Notification(
                type= NotificationType.FOLLOW,
                name= "_sjwneooo",
                id= "@uzinnss",
                content=null,
            )
            Notification(
                type= NotificationType.UNLOCK,
                name= null,
                id= null,
                content=null,
            )
            Notification(
                type= NotificationType.MISSION,
                name= null,
                id= null,
                content=null,
            )
        }
    }
    }
    @Preview(
        device = Devices.PIXEL_4A)
    @Composable
    fun NotificationScreenPreview(){
        NotificationScreen()
    }