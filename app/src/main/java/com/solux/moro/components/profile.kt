package com.solux.moro.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solux.moro.R

@Composable
fun Profile(){
    Column(
        Modifier
            .width((1080/PIXEL_4A_DENSITY).dp)
            .height((1042.88/PIXEL_4A_DENSITY).dp)
            .padding(start = (46.08/PIXEL_4A_DENSITY).dp, top = (46.08/PIXEL_4A_DENSITY).dp, end = (46.08/PIXEL_4A_DENSITY).dp, bottom = (46.08/PIXEL_4A_DENSITY).dp),
        verticalArrangement = Arrangement.spacedBy((57.60000228881836/PIXEL_4A_DENSITY).dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            //horizontalArrangement = Arrangement.spacedBy((23.040000915527344/PIXEL_4A_DENSITY).dp, Alignment.Start),
            //verticalAlignment = Alignment.CenterVertically,
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width((457.92001/PIXEL_4A_DENSITY).dp)
                .height((460.79999/PIXEL_4A_DENSITY).dp)
                .padding(start = (8.64/PIXEL_4A_DENSITY).dp, end = (8.64/PIXEL_4A_DENSITY).dp),
        ) {

                Box(
                    modifier = Modifier
                        .width((457.92001 / PIXEL_4A_DENSITY).dp)
                        .height((460.79999 / PIXEL_4A_DENSITY).dp)

                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(Color(0xFF3366FF),Color(0x003D9900)),
                                center = Offset.Unspecified, // 기본값은 중앙
                                radius = Float.POSITIVE_INFINITY // 기본값은 컴포넌트 크기에 맞춤
                            )
                        )
                )
                Image(
                    modifier = Modifier
                        .width((276.48001 / PIXEL_4A_DENSITY).dp)
                        .height((276.48001 / PIXEL_4A_DENSITY).dp)
                        .clip(RoundedCornerShape(size = (28797.12109 / PIXEL_4A_DENSITY).dp))
                        .border(width = (11.52/ PIXEL_4A_DENSITY).dp, color = Color(0xFF1F2937), shape = RoundedCornerShape(size = (28797.12109/ PIXEL_4A_DENSITY).dp))
                        .padding((0.31706 / PIXEL_4A_DENSITY).dp),
                    painter = painterResource(id = R.drawable.frame),
                    contentDescription = "image description",
                    contentScale = ContentScale.FillHeight,
                )
        }
    }
}
@Preview(device = Devices.PIXEL_4A)
@Composable
fun ProfilePreview(){
    Profile()
}