package com.solux.moro.ui.mission

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.solux.moro.R
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.component.top.TopBar2
import com.solux.moro.core.util.figmaDp

@Composable
fun MissionScreen(
    navController: NavHostController? = null
) {
    Scaffold(
        topBar = { TopBar2() },
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .background(Color(0xFF121212))
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(figmaDp(14f)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                MissionSection()
            }

            item {
                FeedSection()
            }
        }
    }
}

@Composable
fun MissionSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(figmaDp(16f)),
        verticalArrangement = Arrangement.spacedBy(figmaDp(43f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFF27292E),
                    shape = RoundedCornerShape(figmaDp(20f))
                )
                .padding(figmaDp(16f)),
            verticalArrangement = Arrangement.spacedBy(figmaDp(15f)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 헤더
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Today's Mission",
                    fontSize = 18.sp,
                    lineHeight = 28.sp,
                    color = Color(0xFFF2F2F2)
                )

                Text(
                    text = "Refreshes in 8 hours",
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = Color(0xFFF2F2F2)
                )
            }

            // 미션 이미지 영역
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(figmaDp(150f))
                    .background(
                        color = Color(0xFF4B5563),
                        shape = RoundedCornerShape(figmaDp(8f))
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Mission Challenge Image",
                    fontSize = 14.sp,
                    color = Color(0xFFF2F2F2)
                )
            }

            // 설명 텍스트
            Column(
                verticalArrangement = Arrangement.spacedBy(figmaDp(8f))
            ) {
                Text(
                    text = "Capture something that makes you smile",
                    fontSize = 16.sp,
                    lineHeight = 22.4.sp,
                    color = Color(0xFFF2F2F2)
                )

                Text(
                    text = "Share a moment of joy from your day. It could be anything that brings happiness to your life!",
                    fontSize = 14.sp,
                    lineHeight = 19.6.sp,
                    color = Color(0xFFF2F2F2)
                )
            }

            // 버튼 영역
            MissionButtons()
        }
    }
}
@Composable
fun MissionButtons() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(figmaDp(8f))
    ) {

        // Take Mission Photo
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF2F2F2), RoundedCornerShape(figmaDp(8f)))
                .padding(figmaDp(10f)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.icon_black_camera),
                    contentDescription = null,
                    modifier = Modifier.size(figmaDp(16f))
                )
                Text(
                    text = "Take Mission Photo",
                    fontSize = 16.sp,
                    color = Color(0xFF121212),
                    modifier = Modifier.padding(start = figmaDp(8f))
                )
            }
        }

        // My Mission Photo
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF121212), RoundedCornerShape(figmaDp(8f)))
                .padding(figmaDp(10f)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.icon_white_camera),
                    contentDescription = null,
                    modifier = Modifier.size(figmaDp(16f))
                )
                Text(
                    text = "My Mission Photo",
                    fontSize = 16.sp,
                    color = Color(0xFFF2F2F2),
                    modifier = Modifier.padding(start = figmaDp(8f))
                )
            }
        }
    }
}
@Composable
fun FeedSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = figmaDp(16f),
                end = figmaDp(16f),
                bottom = figmaDp(16f)
            ),
        verticalArrangement = Arrangement.spacedBy(figmaDp(10f))
    ) {

        // 타이틀 + 필터
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Mission Feed",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFF2F2F2)
            )

            FilterButton()
        }

        // 사진 그리드
        FeedGrid()
    }
}

@Composable
fun FilterButton() {
    Row(
        modifier = Modifier
            .border(
                width = figmaDp(1f),
                color = Color(0xFFBDBDBD),
                shape = RoundedCornerShape(figmaDp(4f))
            )
            .height(figmaDp(30f))
            .padding(
                start = figmaDp(13f),
                end = figmaDp(13f)
            ),
        horizontalArrangement = Arrangement.spacedBy(figmaDp(4f)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Filter",
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = Color(0xFFEEEEEE)
        )

        Image(
            painter = painterResource(id = R.drawable.chevron_down),
            contentDescription = null,
            modifier = Modifier
                .width(figmaDp(12f))
                .height(figmaDp(10f))
        )
    }
}
@Composable
fun FeedGrid() {
    Column(
        verticalArrangement = Arrangement.spacedBy(figmaDp(10f))
    ) {
        repeat(3) {   // 줄 개수
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(figmaDp(10f))
            ) {
                repeat(3) {   // 한 줄당 3개
                    Image(
                        painter = painterResource(id = R.drawable.eg_coffee),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .width(figmaDp(107.66f))
                            .height(figmaDp(107.66f))
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun MissionScreenPreview() {
    MissionScreen()
}
