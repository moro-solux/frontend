package com.solux.moro.ui.mission

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solux.moro.R
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.component.top.TopBarBack
import com.solux.moro.core.util.figmaDp

@Composable
fun MissionUploadScreen() {

    Scaffold(
        topBar = { TopBarBack("미션 업로드") },
        bottomBar = { BottomBar() }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color(0xFF121212))
                .padding(innerPadding)
                .padding(
                    start = figmaDp(16f),
                    top = figmaDp(16f),
                    end = figmaDp(16f),
                ),
            verticalArrangement = Arrangement.spacedBy(
                figmaDp(15f),
                Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Upload_Section()
            InstagramUpload()
        }
    }
}

@Composable
fun Upload_Section() {
    Row(
        modifier = Modifier
            .border(
                width = figmaDp(1f),
                color = Color(0xFFF2F2F2),
                shape = RoundedCornerShape(figmaDp(24f))
            )
            .width(figmaDp(343f))
            .height(figmaDp(553f)),
        horizontalArrangement = Arrangement.spacedBy(
            figmaDp(8f),
            Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .width(266.17987.dp),
            verticalArrangement = Arrangement.spacedBy(
                figmaDp(16f),
                Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            //mission
            Row(
                modifier = Modifier
                    .width(figmaDp(155f))
                    .height(figmaDp(23f)),
                horizontalArrangement = Arrangement.spacedBy(
                    figmaDp(8f),
                    Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier
                        .width(figmaDp(14f))
                        .height(figmaDp(14f)),
                    painter = painterResource(id = R.drawable.mission),
                    contentDescription = "image description",
                )

                Row(
                    modifier = Modifier
                        .width(428.dp)
                        .height(67.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier
                            .width(figmaDp(149f))
                            .height(figmaDp(23f)),
                        text = "Capture the color",
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 18.sp,
                            lineHeight = 23.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                        )
                    )
                }

            }

            // 미션 컨텐츠 부분
            //TargetCapture()
            MissionCapture()

            Image(
                painter = painterResource(id = R.drawable.moro_logo_m),
                contentDescription = "image description",
            )
        }
    }
}

@Composable
fun Upload_Button() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(figmaDp(54f))
            .background(
                color = Color(0xFFF2F2F2),
                shape = RoundedCornerShape(figmaDp(12f))
            )
            .padding(
                start = figmaDp(16f),
                top = figmaDp(16f),
                end = figmaDp(16f),
                bottom = figmaDp(16f)
            ),
        verticalArrangement = Arrangement.spacedBy(
            figmaDp(8f),
            Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .width(figmaDp(120f))
                .height(figmaDp(24f)),
            horizontalArrangement = Arrangement.spacedBy(
                figmaDp(16f),
                Alignment.Start
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.upload_cloud),
                contentDescription = "image description",
                contentScale = ContentScale.None
            )
            Row(
                modifier = Modifier
                    .width(figmaDp(44f))
                    .height(figmaDp(22f)),
                horizontalArrangement = Arrangement.spacedBy(
                    figmaDp(8f),
                    Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "업로드",
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 22.4.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF121212),
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
    }
}

@Composable
fun InstagramUpload(){
    Row(
        modifier = Modifier
            .width(figmaDp(138f))
            .height(figmaDp(78f)),
        horizontalArrangement = Arrangement.spacedBy(figmaDp(20f), Alignment.Start),
        verticalAlignment = Alignment.Top,
    ) {
        //인스타
        Column(
            verticalArrangement = Arrangement.spacedBy(figmaDp(4f), Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.instagram),
                contentDescription = "image description",
                contentScale = ContentScale.None
            )

            Text(
                modifier = Modifier
                    .width(figmaDp(64f))
                    .height(figmaDp(20f)),
                text = "Instagram",

                // Body2/Regular/14px
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 19.6.sp,
                    //fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                )
            )
        }

        //저장
        Column(
            verticalArrangement = Arrangement.spacedBy(figmaDp(4f), Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .border(
                        width = figmaDp(1f),
                        color = Color(0xFFF2F2F2),
                        shape = RoundedCornerShape(size = figmaDp(27f))
                    )
                    .width(figmaDp(54f))
                    .height(figmaDp(54f))
                    ,
                horizontalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.download),
                    contentDescription = "image description",
                    contentScale = ContentScale.None
                )

            }
            Text(
                modifier = Modifier
                    .width(figmaDp(26f))
                    .height(figmaDp(20f)),
                text = "저장",

                // Body2/Regular/14px
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 19.6.sp,
                    // fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}

@Composable
fun MissionCapture() {
    Column(
        modifier = Modifier
            .width(figmaDp(266.17987f))
            .height(figmaDp(413.13202f))
            .padding(
                start = figmaDp(14.12626f),
                top = figmaDp(14.12626f),
                end = figmaDp(14.12626f),
                bottom = figmaDp(14.12626f)
            ),
        verticalArrangement = Arrangement.spacedBy(
            figmaDp(9.338271141052246f),
            Alignment.Top
        ),
        horizontalAlignment = Alignment.Start,
    ) {
        //id
        Row(
            modifier = Modifier
                .width(figmaDp(237.92735f))
                .height(figmaDp(33.95735f)),
            horizontalArrangement = Arrangement.spacedBy(
                figmaDp(8.828909873962402f),
                Alignment.Start
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                Modifier
                    .size(figmaDp(33.95735f))
                    .clip(CircleShape)
                    .background(color = Color(0xFFD9D9D9))
            )
            Row(
                modifier = Modifier
                    .width(figmaDp(195.1411f))
                    .height(figmaDp(33.95735f)),
                horizontalArrangement = Arrangement.spacedBy(
                    figmaDp(0f),
                    Alignment.Start
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier
                        .width(figmaDp(80f))
                        .height(figmaDp(20f)),
                    horizontalArrangement = Arrangement.spacedBy(
                        figmaDp(6.18023681640625f),
                        Alignment.Start
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "@colorlover",
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 14.13.sp,
                            lineHeight = 19.78.sp,
                            //fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFFF2F2F2),
                        )
                    )
                }
            }
        }

        //미션
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier
                    .width(figmaDp(237.92735f))
                    .height(figmaDp(15.00915f)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "노을빛을 찾아보세요 🌇",
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 10.59.sp,
                        lineHeight = 14.83.sp,
                        // fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFA5A5A5),
                    )
                )

                //퍼센트
                Row(
                    Modifier
                        .width(figmaDp(76.98497f))
                        .height(figmaDp(15.00915f)),
                    horizontalArrangement = Arrangement.spacedBy(
                        figmaDp(3.6385810375213623f),
                        Alignment.Start
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TargetColorSmall()

                    Row(
                        modifier = Modifier
                            .width(figmaDp(21f))
                            .height(figmaDp(15f)),
                        horizontalArrangement = Arrangement.spacedBy(
                            figmaDp(0f),
                            Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "97%",
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 10.59.sp,
                                lineHeight = 14.83.sp,
                                // fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFFA5A5A5),
                            )
                        )
                    }
                }
            }
        }

        Box(
            Modifier
                .fillMaxWidth()
                .height(figmaDp(317.23648f))
                .background(
                    color = Color(0xFFD9D9D9),
                    shape = RoundedCornerShape(figmaDp(8.83f))
                )
        )
    }
}

@Composable
fun TargetCapture() {
    Column(
        modifier = Modifier
            .width(figmaDp(193.5f))
            .height(figmaDp(403f)),
        verticalArrangement = Arrangement.spacedBy(
            figmaDp(16f),
            Alignment.Top
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        //타겟 컬러
        TargetColor()
        Box(
            Modifier
                .width(figmaDp(151f))
                .height(figmaDp(205f))
                .background(
                    color = Color(0xFF525252),
                    shape = RoundedCornerShape(figmaDp(12f))
                )
                .background(
                    color = Color(0x00000000),
                    shape = RoundedCornerShape(figmaDp(12f))
                )
        )

        Row(
            modifier = Modifier
                .width(figmaDp(66f))
                .height(figmaDp(45f))
                .background(color = Color(0x00000000)),
            horizontalArrangement = Arrangement.spacedBy(
                figmaDp(8f),
                Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "92%",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 32.sp,
                    lineHeight = 44.8.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                )
            )
        }



        Row(
            horizontalArrangement = Arrangement.spacedBy(
                figmaDp(28f),
                Alignment.Start
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            //color
            Column(
                modifier = Modifier
                    .width(figmaDp(60f))
                    .height(figmaDp(72f)),
                verticalArrangement = Arrangement.spacedBy(
                    figmaDp(8f),
                    Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    Modifier
                        .border(
                            width = figmaDp(2f),
                            color = Color(0xFFEEEEEE),
                            shape = RoundedCornerShape(figmaDp(24f))
                        )
                        .width(figmaDp(48f))
                        .height(figmaDp(48f))
                        .background(
                            color = Color(0xFF4070FF),
                            shape = RoundedCornerShape(figmaDp(24f))
                        )
                )

                Row(
                    modifier = Modifier
                        .width(figmaDp(37f))
                        .height(figmaDp(16f)),
                    horizontalArrangement = Arrangement.spacedBy(
                        figmaDp(8f),
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Target",
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
                painter = painterResource(id = R.drawable.right),
                contentDescription = "image description"
            )

            Column(
                modifier = Modifier
                    .width(figmaDp(60f))
                    .height(figmaDp(72f)),
                verticalArrangement = Arrangement.spacedBy(
                    figmaDp(8f),
                    Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    Modifier
                        .border(
                            width = figmaDp(2f),
                            color = Color(0xFFEEEEEE),
                            shape = RoundedCornerShape(figmaDp(24f))
                        )
                        .width(figmaDp(48f))
                        .height(figmaDp(48f))
                        .background(
                            color = Color(0xFF4070FF),
                            shape = RoundedCornerShape(figmaDp(24f))
                        )
                )

                Row(
                    modifier = Modifier
                        .height(figmaDp(16f)),
                    horizontalArrangement = Arrangement.spacedBy(
                        figmaDp(8f),
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Your Color",
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFF2F2F2),
                        )
                    )
                }
            }
        }
    }
}


@Composable
fun TargetColor(){
    Row(
        modifier = Modifier
            .border(
                width = figmaDp(1f),
                color = Color(0xFFF2F2F2),
                shape = RoundedCornerShape(figmaDp(9999f))
            )
            .width(figmaDp(107f))
            .height(figmaDp(33f))
            .background(
                color = Color(0xFF121212),
                shape = RoundedCornerShape(figmaDp(9999f))
            )
            .padding(
                start = figmaDp(11f),
                top = figmaDp(8f),
                end = figmaDp(11f),
                bottom = figmaDp(8f)
            ),
        horizontalArrangement = Arrangement.spacedBy(
            figmaDp(8f),
            Alignment.Start
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier
                .border(
                    width = figmaDp(0f),
                    color = Color(0xFFE5E7EB),
                    shape = RoundedCornerShape(figmaDp(9999f))
                )
                .width(figmaDp(16f))
                .height(figmaDp(16f))
                .background(
                    color = Color(0xFF3366FF),
                    shape = RoundedCornerShape(figmaDp(9999f))
                )
        )

        Row(
            modifier = Modifier
                .width(figmaDp(61f))
                .height(figmaDp(17f)),
            horizontalArrangement = Arrangement.spacedBy(
                figmaDp(8f),
                Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "#3366FF",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFF2F2F2),
                    textAlign = TextAlign.Center,
                )
            )
        }
    }

}

@Composable
fun TargetColorSmall() {
    Row(
        modifier = Modifier
            .border(
                width = figmaDp(0.45482f),
                color = Color(0xFFF2F2F2),
                shape = RoundedCornerShape(size = figmaDp(4547.77148f))
            )
            .width(figmaDp(48.92184f))
            .height(figmaDp(15.00915f))
            .background(
                color = Color(0xFF121212),
                shape = RoundedCornerShape(size = figmaDp(4547.77148f))
            )
            .padding(
                start = figmaDp(5.00305f),
                top = figmaDp(3.63858f),
                end = figmaDp(5.00305f),
                bottom = figmaDp(3.63858f)
            ),
        horizontalArrangement = Arrangement.spacedBy(
            figmaDp(3.6385810375213623f),
            Alignment.Start
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier
                .border(
                    width = figmaDp(0f),
                    color = Color(0xFFE5E7EB),
                    shape = RoundedCornerShape(size = figmaDp(4547.77148f))
                )
                .width(figmaDp(7.27716f))
                .height(figmaDp(7.27716f))
                .background(
                    color = Color(0xFF3366FF),
                    shape = RoundedCornerShape(size = figmaDp(4547.77148f))
                )
        )

        Text(
            text = "#3366FF",
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 6.37.sp,
                // fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(400),
                color = Color(0xFFF2F2F2),
                textAlign = TextAlign.Center,
            )
        )
    }
}


@Preview
@Composable
fun MissionUploadPreview() {
    MissionUploadScreen()
}
