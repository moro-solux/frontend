package com.solux.moro.ui.profile.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.solux.moro.R
import com.solux.moro.ui.home.FeedItem

@SuppressLint("DiscouragedApi")
@Composable
fun Captures(
    posts: List<FeedItem>
){
    Column(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .height(1091.65552.toPxDp)
            .padding(start = 46.08.toPxDp, end = 46.08.toPxDp),
        verticalArrangement = Arrangement.spacedBy(28.80000114440918.toPxDp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .width(987.84003.toPxDp)
                .height(73.toPxDp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Recent Captures",
                style = TextStyle(
                    fontSize = 51.84.toPxSp,
                    lineHeight = 72.58.toPxSp,
//                   fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFFF2F2F2),
                ),
                modifier = Modifier
                    .width(405.toPxDp)
                    .height(73.toPxDp)
            )
        }
        val context = LocalContext.current
        val captureImages = (1..9).map { i ->
            context.resources.getIdentifier("imp_captures_$i", "drawable", context.packageName)
        }
        ImageFeedGrid(posts)

    }
}

@Composable
fun ImageFeedGrid(posts: List<FeedItem>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .height(989.85.toPxDp),
        verticalArrangement = Arrangement.spacedBy(28.8.toPxDp),
        horizontalArrangement = Arrangement.spacedBy(28.8.toPxDp) 
    ) {
        items(posts) { post ->
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp)) // 살짝 둥글게 처리
                    .background(Color(0xFF2B2B2B)) // 로딩 전 기본 배경색
            ) {
                AsyncImage(
                    model = post.imageUrl?: R.drawable.imp_captures_1,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
@Preview(
    device = Devices.PIXEL_4A)
@Composable
fun CapturePreview(){
   // Captures()
}