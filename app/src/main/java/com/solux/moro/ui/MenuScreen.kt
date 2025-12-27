package com.solux.moro.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.component.top.TopBarBack
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import com.solux.moro.core.util.figmaDp

@Composable
fun MenuScreen() {
    var isPublic by remember { mutableStateOf(true) }
    var isPushOn by remember { mutableStateOf(false) }


    Scaffold(
        topBar = { TopBarBack("메뉴") },
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
                Settings()
            }
            item {
                Setting_3(
                    isPublic = isPublic,
                    onPublicToggle = { isPublic = it },
                    isPushOn = isPushOn,
                    onPushToggle = { isPushOn = it }
                )

            }
        }
    }
}

@Composable
fun Settings(
)
{
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = figmaDp(16f),
                top = figmaDp(20f),
                end = figmaDp(16f),
                bottom = figmaDp(16f)
            ),
        verticalArrangement = Arrangement.spacedBy(figmaDp(12f), Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(figmaDp(28f)),
            horizontalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "설정",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 28.sp,
                    //fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFFFFFFFF),
                )
            )
        }
        Column(
            modifier = Modifier
                .border(
                    width = figmaDp(1f),
                    color = Color(0xFFF2F2F2),
                    shape = RoundedCornerShape(size = figmaDp(16f))
                )
                .fillMaxWidth()
                .height(figmaDp(80f))
                .background(
                    color = Color(0xFF171717),
                    shape = RoundedCornerShape(size = figmaDp(16f))
                )
                .padding(
                    start = figmaDp(20f),
                    top = figmaDp(20f),
                    end = figmaDp(20f),
                    bottom = figmaDp(20f)
                ),
            verticalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(figmaDp(40f)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier
                        .width(figmaDp(263f))
                        .height(figmaDp(24f)),
                    horizontalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier
                            .width(figmaDp(89f))
                            .height(figmaDp(24f)),
                        text = "colormap",
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 20.sp,
                            lineHeight = 24.sp,
                            //fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFFFFFFFF),
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .size(figmaDp(40f))
                        .background(
                            color = Color(0xFF464646),
                            shape = RoundedCornerShape(size = figmaDp(8f))
                        )
                        .padding(
                            start = figmaDp(4f),
                            top = figmaDp(4f),
                            end = figmaDp(4f),
                            bottom = figmaDp(4f)
                        ),
                    horizontalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(figmaDp(32f))
                            .clip(RoundedCornerShape(figmaDp(4f)))
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFFFF6CB4),
                                        Color(0xFFFFBC4F),
                                        Color(0xFFDEFF9C),
                                        Color(0xFFCAFFC6)
                                    ),
                                    start = Offset.Zero,
                                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                                )
                            )
                    )

                }
            }
        }
    }

}


@Composable
fun Setting_3(
    isPublic: Boolean,
    onPublicToggle: (Boolean) -> Unit,
    isPushOn: Boolean,
    onPushToggle: (Boolean) -> Unit
)
 {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = figmaDp(16f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(figmaDp(48f))
                .clickable { onPublicToggle(!isPublic) },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "공개 / 비공개 설정",
                fontSize = 16.sp,
                color = Color(0xFFF2F2F2)
            )

            ToggleButton(
                isOn = isPublic,
                onToggle = onPublicToggle
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(figmaDp(48f))
                .clickable { onPushToggle(!isPushOn) },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "푸시 알림 설정",
                fontSize = 16.sp,
                color = Color(0xFFF2F2F2)
            )

            ToggleButton(
                isOn = isPushOn,
                onToggle = onPushToggle
            )
        }



    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = figmaDp(16f),
                top = figmaDp(16f),
                end = figmaDp(16f),
                bottom = figmaDp(16f)
            ),
        horizontalArrangement = Arrangement.spacedBy(figmaDp(6f), Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "로그아웃",

            // Body1/Regular/16px
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                lineHeight = 22.4.sp,
                //fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(400),
                color = Color(0xFFA5A5A5),
            )
        )
    }
}

@Composable
fun ToggleButton(
    isOn: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .width(figmaDp(48f))
            .height(figmaDp(24f))
            .clip(RoundedCornerShape(figmaDp(9999f)))
            .background(
                color = if (isOn) Color(0xFFF2F2F2) else Color(0xFF737373)
            )
            .clickable { onToggle(!isOn) }
            .padding(figmaDp(2f)),
        horizontalArrangement = if (isOn)
            Arrangement.Start else Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(figmaDp(20f))
                .clip(CircleShape)
                .background(
                    color = if (isOn) Color(0xFFBDBDBD) else Color.White
                )
                .border(
                    width = figmaDp(2f),
                    color = Color(0xFFF2F2F2),
                    shape = CircleShape
                )
        )
    }
}

@Preview
@Composable
fun MenuScreenPreview() {
    MenuScreen()
}
