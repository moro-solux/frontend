package com.solux.moro.screens

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
import com.solux.moro.R
import com.solux.moro.components.BottomBar
import com.solux.moro.components.top.TopBarBack
import com.solux.moro.components.top.figmaDp

@Composable
fun MyMissionScreen() {
    Scaffold(
        topBar = { TopBarBack("My Mission") },
        bottomBar = { BottomBar() }
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
                MyMissionSection()
            }

        }
    }
}

@Composable
fun MyMissionSection() {

    Column(
        modifier = Modifier
            .width(figmaDp(375f))
            .height(figmaDp(348f))
            .padding(
                start = figmaDp(16f),
                top = figmaDp(20f),
                end = figmaDp(16f),
                bottom = figmaDp(16f)
            ),
        verticalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        MyMission()
        F_MyMission()
    }
}

@Composable
fun MyMission(
    title: String = "노을빛을 찾아보세요 🌇"
) {
    Column(
        modifier = Modifier
            .border(
                width = figmaDp(1f),
                color = Color(0xFF262626),
                shape = RoundedCornerShape(size = figmaDp(16f))
            )
            .fillMaxWidth()
            .height(figmaDp(100f))
            .background(
                color = Color(0xFF171717),
                shape = RoundedCornerShape(size = figmaDp(16f))
            )
            .padding(
                start = figmaDp(16f),
                top = figmaDp(16f),
                end = figmaDp(16f),
                bottom = figmaDp(16f)
            ),
        verticalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(figmaDp(68f)),
            horizontalArrangement = Arrangement.spacedBy(figmaDp(12f), Alignment.Start),
            verticalAlignment = Alignment.Top,
        ) {
            Row(
                modifier = Modifier
                    .border(
                        width = figmaDp(0f),
                        color = Color(0xFFE5E7EB),
                        shape = RoundedCornerShape(size = figmaDp(12f))
                    )
                    .width(figmaDp(64f))
                    .height(figmaDp(64f))
                    .background(
                        color = Color(0xFF404040),
                        shape = RoundedCornerShape(size = figmaDp(12f))
                    )
                    .padding(
                        start = figmaDp(22f),
                        top = figmaDp(22f),
                        end = figmaDp(22f),
                        bottom = figmaDp(22f)
                    ),
                horizontalArrangement = Arrangement.spacedBy(figmaDp(0f), Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // 이미지 삽입
            }
            //텍스트
            Column(
                verticalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Top),
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .width(figmaDp(8f))
                            .height(figmaDp(8f))
                            .background(
                                color = Color(0xFFF2F2F2),
                                shape = RoundedCornerShape(size = figmaDp(9999f))
                            )
                    )

                    Text(
                        text = "진행중",
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFF2F2F2),
                        )
                    )
                }

                Row(
                    modifier = Modifier
                        .width(figmaDp(205.25f))
                        .height(figmaDp(20f)),
                    horizontalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = title,
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFFFFFFF),
                        )
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "촬영일시: -",
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFF2F2F2),
                        )
                    )
                }
            }

            Image(
                painter = painterResource(id = R.drawable.chevron_right),
                contentDescription = "image description",
            )
        }
    }
}

@Composable
fun F_MyMission(
    title: String = "하늘색을 담아보세요 ☁️",
    shotAt: String = "촬영일시: 2025.11.05 오후 2:30",
) {
    Column(
        modifier = Modifier
            .border(
                width = figmaDp(1f),
                color = Color(0xFF262626),
                shape = RoundedCornerShape(size = figmaDp(16f))
            )
            .fillMaxWidth()
            .height(figmaDp(100f))
            .background(
                color = Color(0xFF171717),
                shape = RoundedCornerShape(size = figmaDp(16f))
            )
            .padding(
                start = figmaDp(16f),
                top = figmaDp(16f),
                end = figmaDp(16f),
                bottom = figmaDp(16f)
            ),
        verticalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(figmaDp(68f)),
            horizontalArrangement = Arrangement.spacedBy(figmaDp(12f), Alignment.Start),
            verticalAlignment = Alignment.Top,
        ) {
            Row(
                modifier = Modifier
                    .border(
                        width = figmaDp(0f),
                        color = Color(0xFFE5E7EB),
                        shape = RoundedCornerShape(size = figmaDp(12f))
                    )
                    .width(figmaDp(64f))
                    .height(figmaDp(64f))
                    .background(
                        color = Color(0xFF404040),
                        shape = RoundedCornerShape(size = figmaDp(12f))
                    )
                    .padding(
                        start = figmaDp(22f),
                        top = figmaDp(22f),
                        end = figmaDp(22f),
                        bottom = figmaDp(22f)
                    ),
                horizontalArrangement = Arrangement.spacedBy(figmaDp(0f), Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // 이미지 삽입
            }
            //텍스트
            Column(
                verticalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Top),
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .width(figmaDp(8f))
                            .height(figmaDp(8f))
                            .background(
                                color = Color(0xFFA5A5A5),
                                shape = RoundedCornerShape(size = figmaDp(9999f))
                            )
                    )

                    Text(
                        text = "완료",
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFA5A5A5),
                        )
                    )
                }

                Row(
                    modifier = Modifier
                        .width(figmaDp(205.25f))
                        .height(figmaDp(20f)),
                    horizontalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = title,
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFA5A5A5),
                        )
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = shotAt,
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFA5A5A5),
                        )
                    )
                }
            }

            Image(
                painter = painterResource(id = R.drawable.chevron_right_disabled),
                contentDescription = "image description",
            )
        }
    }
}

@Preview
@Composable
fun MyMissionScreenPreview() {
    MyMissionScreen()
}
