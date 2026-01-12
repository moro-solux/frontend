package com.solux.moro.ui.profile.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solux.moro.R

@Composable
fun ProfileHeader(
    nickname: String,
    colorCode: String?="null",
){
    Column(
        Modifier
            .width(1080.toPxDp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(457.92001.toPxDp)
                .padding(start = 8.64.toPxDp, end = 8.64.toPxDp),
        ) {

            Box(
                modifier = Modifier
                    .width(457.92001.toPxDp)
                    .height(460.79999.toPxDp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF3366FF), Color(0x003D9900)),
                            center = Offset.Unspecified, // 기본값은 중앙
                            radius = Float.POSITIVE_INFINITY // 기본값은 컴포넌트 크기에 맞춤
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .width(276.48001.toPxDp)
                    .height(276.48001.toPxDp)
            ) {
                Image(
                    modifier = Modifier
                        .width(276.48001.toPxDp)
                        .height(276.48001.toPxDp)
                        .clip(RoundedCornerShape(size = 28797.12109.toPxDp))
                        .border(
                            width = 11.52.toPxDp,
                            color = Color(0xFF1F2937),
                            shape = RoundedCornerShape(size = 28797.12109.toPxDp)
                        )
                        .padding(0.31706.toPxDp)
                        .background(Color.White),
                    painter = painterResource(id = R.drawable.img_profile_large),
                    contentDescription = "image description",
                    contentScale = ContentScale.FillHeight,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        0.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .offset(x = 207.35938.toPxDp, y = 207.35938.toPxDp)
                        .border(
                            width = 5.76.toPxDp,
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(size = 28797.12109.toPxDp)
                        )
                        .width(69.12.toPxDp)
                        .height(69.12.toPxDp)
                        .background(
                            color = Color(0xFF1F2937),
                            shape = RoundedCornerShape(size = 28797.12109.toPxDp)
                        )
                        .padding(
                            start = 17.27988.toPxDp,
                            top = 17.28008.toPxDp,
                            end = 17.28012.toPxDp,
                            bottom = 17.27992.toPxDp
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_palette),
                        contentDescription = "image description",
                        contentScale = ContentScale.None
                    )
                }
            }
        }
        Column(
            Modifier
                .width(987.84003.toPxDp),
            verticalArrangement = Arrangement.spacedBy(34.560001373291016.toPxDp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                Modifier
                    .width(987.84003.toPxDp)
                    .height(56.toPxDp)
                    .padding(start = 408.96002.toPxDp, end = 408.96002.toPxDp),
                horizontalArrangement = Arrangement.spacedBy(
                    23.040000915527344.toPxDp,
                    Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = colorCode?:"null",
                    style = TextStyle(
                        fontSize = 40.32.toPxSp,
                        lineHeight = 56.45.toPxSp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFF2F2F2),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .width(170.toPxDp)
                        .height(56.toPxDp)
                )
            }
            Text(
                text = nickname,
                style = TextStyle(
                    fontSize = 51.84.toPxSp,
                    lineHeight = 72.58.toPxSp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFFF2F2F2),

                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .width(987.84003.toPxDp)
                    .height(80.64.toPxDp)
            )
        }
    }
}
@Preview(device = Devices.PIXEL_4A)
@Composable
fun ProfileHeaderPreview(){
    ProfileHeader(
        "@colorhunter",
        "#3366FF"
    )
}
