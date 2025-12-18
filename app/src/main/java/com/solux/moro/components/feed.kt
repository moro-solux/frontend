package com.solux.moro.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solux.moro.R


@Composable
fun Feed() {
    Column(
        verticalArrangement = Arrangement.spacedBy((30.461544036865234/PIXEL_4A_DENSITY).dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .height((1442.17859/PIXEL_4A_DENSITY).dp)
            .padding(start = (46.08/PIXEL_4A_DENSITY).dp, top = (46.08/PIXEL_4A_DENSITY).dp, end = (46.08/PIXEL_4A_DENSITY).dp, bottom = (46.08/PIXEL_4A_DENSITY).dp)
    ) {//피드 컬럼 ( /PIXEL_4A_DENSITY)
        Row(
            Modifier
                .width((987.84003/PIXEL_4A_DENSITY).dp)
                .height((110.76924/PIXEL_4A_DENSITY).dp),
            horizontalArrangement = Arrangement.spacedBy((28.80000114440918/PIXEL_4A_DENSITY).dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier
                    .padding(0.dp)
                    .width((110.76924/PIXEL_4A_DENSITY).dp)
                    .height((110.76924/PIXEL_4A_DENSITY).dp)
                    .clip(CircleShape)
                    .background(color = Color(0xFFF2F2F2)),
                painter = painterResource(id = R.drawable.frame),
                contentDescription = "image description",
                contentScale = ContentScale.FillHeight
            )
            Row(
                Modifier
                    .width((847.38458/PIXEL_4A_DENSITY).dp)
                    .height((110.76924/PIXEL_4A_DENSITY).dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy((
                        20.15999984741211/PIXEL_4A_DENSITY).dp,
                        Alignment.Start
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .width((393.48001/PIXEL_4A_DENSITY).dp)
                        .height((65/PIXEL_4A_DENSITY).dp)
                ) {
                    Text(
                        text = "@colorlover",
                        // Body1/SemiBold/16px
                        style = TextStyle(
                            fontSize = (46.08/PIXEL_4A_DENSITY).sp,
                            lineHeight = (64.51/PIXEL_4A_DENSITY).sp,
//////폰트 다운       fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFFF2F2F2),
                        ),
                        modifier = Modifier
                            .width((261/PIXEL_4A_DENSITY).dp)
                            .height((65/PIXEL_4A_DENSITY).dp)
                    )
                    Text(
                        text = "2h ago",

                        // Body3/Regular/12px
                        style = TextStyle(
                            fontSize = (34.56/PIXEL_4A_DENSITY).sp,
                            lineHeight = (48.38/PIXEL_4A_DENSITY).sp,
//////폰트 다운     //fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFFA5A5A5),
                        ),
                        modifier = Modifier
                            .width((112.32001/PIXEL_4A_DENSITY).dp)
                            .height((48/PIXEL_4A_DENSITY).dp)
                    )
                }
                IconButton(onClick = { /*  */ },
                    modifier = Modifier.size((69.12/PIXEL_4A_DENSITY).dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options",
                        tint = Color.White,
                        modifier = Modifier
                            .padding((2.88/PIXEL_4A_DENSITY).dp)
                            .size((69.12/PIXEL_4A_DENSITY).dp)

                    )
                }
            }
        }

        Row(
            Modifier
                .width((987.84003/PIXEL_4A_DENSITY).dp)
                .height((1044.4801/PIXEL_4A_DENSITY).dp)
                .padding(top = (28.8/PIXEL_4A_DENSITY).dp, bottom = (28.8/PIXEL_4A_DENSITY).dp),
            horizontalArrangement = Arrangement.spacedBy((23.040000915527344/PIXEL_4A_DENSITY).dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Image(
                //피드 화면
                modifier = Modifier
                    .width((740.16003/PIXEL_4A_DENSITY).dp)
                    .height((986.88007/PIXEL_4A_DENSITY).dp),
                painter = painterResource(id = R.drawable.feed_img),
                contentDescription = "image description",
                contentScale = ContentScale.FillBounds,

                )

            Column(
                Modifier// 색상 칩
                    .width((224.64001/PIXEL_4A_DENSITY).dp)
                    .height((986.88007/PIXEL_4A_DENSITY).dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
            ) {
                Box(
                    Modifier
                        .width((224.64001/PIXEL_4A_DENSITY).dp)
                        .height((224.64001/PIXEL_4A_DENSITY).dp)
                        .background(
                            color = Color(0xFF4982E5),
                            shape = RoundedCornerShape(size = (3.84615/PIXEL_4A_DENSITY).dp)
                        )
                )
                Box(
                    Modifier
                        .width((224.64001/PIXEL_4A_DENSITY).dp)
                        .height((224.64001/PIXEL_4A_DENSITY).dp)
                        .background(
                            color = Color(0xFF032564),
                            shape = RoundedCornerShape(size = (3.84615/PIXEL_4A_DENSITY).dp)
                        )
                )
                Box(
                    Modifier
                        .width((224.64001/PIXEL_4A_DENSITY).dp)
                        .height((224.64001/PIXEL_4A_DENSITY).dp)
                        .background(
                            color = Color(0xFFC8D7EC),
                            shape = RoundedCornerShape(size = (3.84615/PIXEL_4A_DENSITY).dp)
                        )
                )
                Box(
                    Modifier
                        .width((224.64001/PIXEL_4A_DENSITY).dp)
                        .height((224.64001/PIXEL_4A_DENSITY).dp)
                        .background(
                            color = Color(0xFF98ADE2),
                            shape = RoundedCornerShape(size = (3.84615/PIXEL_4A_DENSITY).dp)
                        )
                )
            }
        }
        Row(
            Modifier// 색상 코드
                .width((987.84003/PIXEL_4A_DENSITY).dp)
                .height((48/PIXEL_4A_DENSITY).dp),
            horizontalArrangement = Arrangement.spacedBy(22.15384864807129.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(//1st color code
                text = "#FF6025 ",
                style = TextStyle(
                    fontSize = (34.56/PIXEL_4A_DENSITY).sp,
                    lineHeight = (48.38/PIXEL_4A_DENSITY).sp,
////                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFFA5A5A5),
                ),
                modifier = Modifier
                    .width((143/PIXEL_4A_DENSITY).dp)
                    .height((48/PIXEL_4A_DENSITY).dp)
            )
            Text(//2nd color code
                text = "#FF6025 ",
                style = TextStyle(
                    fontSize = (34.56/PIXEL_4A_DENSITY).sp,
                    lineHeight = (48.38/PIXEL_4A_DENSITY).sp,
                    //////                   fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFFA5A5A5),

                    ), modifier = Modifier
                    .width((143/PIXEL_4A_DENSITY).dp)
                    .height((48/PIXEL_4A_DENSITY).dp)
            )
            Text(//3th color code
                text = "#FF6025 ",
                style = TextStyle(
                    fontSize = (34.56/PIXEL_4A_DENSITY).sp,
                    lineHeight = (48.38/PIXEL_4A_DENSITY).sp,
///                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFFA5A5A5),
                ), modifier = Modifier
                    .width((143/PIXEL_4A_DENSITY).dp)
                    .height((48/PIXEL_4A_DENSITY).dp)
            )
            Text(
                text = "#FF6025 ",
                style = TextStyle(
                    fontSize = (34.56/PIXEL_4A_DENSITY).sp,
                    lineHeight = (48.38/PIXEL_4A_DENSITY).sp,
                    ////                fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFFA5A5A5),
                ), modifier = Modifier
                    .width((143/PIXEL_4A_DENSITY).dp)
                    .height((48/PIXEL_4A_DENSITY).dp)
            )
        }
        Row(
            Modifier
                .width((987.84003/PIXEL_4A_DENSITY).dp)
                .height((55.38462/PIXEL_4A_DENSITY).dp),
            horizontalArrangement = Arrangement.spacedBy((28.80000114440918/PIXEL_4A_DENSITY).dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // 좋아요, 공유
            Row(
                horizontalArrangement = Arrangement.spacedBy((11.52/PIXEL_4A_DENSITY).dp, Alignment.Start)
            ){
                IconButton(onClick = { /*  */ },
                    modifier = Modifier.size((55.38462/PIXEL_4A_DENSITY).dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.chat),
                        contentDescription = "More options",
                        tint = Color.White,
                        modifier = Modifier
                            .padding((0.09736/PIXEL_4A_DENSITY).dp)
                            .size((49.85878/PIXEL_4A_DENSITY).dp)

                    )
                }
                Text(
                    text = "8",
                    style = TextStyle(
                        fontSize = (40.32/PIXEL_4A_DENSITY).sp,
                        lineHeight = (56.45/PIXEL_4A_DENSITY).sp,
   //                     fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFF2F2F2),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .width((24.92308/PIXEL_4A_DENSITY).dp)
                        .height((55.38462/PIXEL_4A_DENSITY).dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy((11.52/PIXEL_4A_DENSITY).dp, Alignment.Start)
            ){
                IconButton(onClick = { /*  */ },
                    modifier = Modifier.size((55.38462/PIXEL_4A_DENSITY).dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.share),
                        contentDescription = "More options",
                        tint = Color.White,
                        modifier = Modifier
                            .padding((0.09736/PIXEL_4A_DENSITY).dp)
                            .size((49.85878/PIXEL_4A_DENSITY).dp)
                    )
                }
                Text(
                    text = "8",
                    style = TextStyle(
                        fontSize = (40.32/PIXEL_4A_DENSITY).sp,
                        lineHeight = (56.45/PIXEL_4A_DENSITY).sp,
                        //                     fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFF2F2F2),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .width((24.92308/PIXEL_4A_DENSITY).dp)
                        .height((55.38462/PIXEL_4A_DENSITY).dp)
                )
            }
        }
    }
}


@Preview(
    device = Devices.PIXEL_4A)
@Composable
fun FeedPreview(){
    Feed()
}