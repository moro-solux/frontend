package com.solux.moro.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@SuppressLint("DiscouragedApi")
@Composable
fun Captures(){
    Column(
        modifier = Modifier
            .width(1080.toPxDp)
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
        ImageFeedGrid(captureImages)

    }
}

@Composable
fun ImageFeedGrid(images: List<Int>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .height(989.85.toPxDp),
        verticalArrangement = Arrangement.spacedBy(28.8.toPxDp),
        horizontalArrangement = Arrangement.spacedBy(28.8.toPxDp) 
    ) {
        items(images) { imageRes ->
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
    }
}
@Preview(
    device = Devices.PIXEL_4A)
@Composable
fun CapturePreview(){
    Captures()
}