package com.solux.moro.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.solux.moro.R
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Palette() {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(478.07999.toPxDp)
                .clip(CircleShape)
                .background(Color(0xFF8EEAF4)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_edit),
                contentDescription = null,
                modifier = Modifier.size(57.10587.toPxDp)
            )
        }
        SemiCircleItems(
            itemColors = listOf(
                Color(0xFF929DF7),
                Color(0xFF93CBF5),
                Color(0xFF7D48DA),
                Color(0xFF4934BF),
                Color(0xFF631577)
            )
        )
    }
}

@Composable
fun SemiCircleItems(
    itemCount: Int = 6,
    radius: Dp = 478.07999.toPxDp,
    itemSize: Dp = 175.97812.toPxDp,
    itemColors: List<Color> = mutableListOf(Color(0x00000000), Color(0x00000000), Color(0x00000000),Color(0x00000000), Color(0x00000000), Color(0x00000000)),
) {
    Box(
        modifier = Modifier
            .size(radius * 2 + itemSize),
        contentAlignment = Alignment.Center
    ) {
        val angleStep = 180f / (itemCount - 1)

        repeat(itemCount) { index ->
            val angleDeg = -90f + angleStep * index
            val angleRad = Math.toRadians(angleDeg.toDouble())

            val x = radius * cos(angleRad).toFloat()
            val y = radius * sin(angleRad).toFloat()

            val color = itemColors.getOrNull(index) ?: Color.Transparent

            if(color==Color.Transparent){
                Box(
                    modifier = Modifier
                        .size(itemSize)
                        .offset(x = x, y = y)
                        .clip(CircleShape)
                        .background(color)
                        .border(color=Color.White, width = 2.88.toPxDp, shape = CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                }
            }
            else{
                Box(
                    modifier = Modifier
                        .size(itemSize)
                        .offset(x = x, y = y)
                        .clip(CircleShape)
                        .background(color),
                    contentAlignment = Alignment.Center,
                ) {
                }
            }
        }
    }
}


@Preview(device = Devices.PIXEL_4A)
@Composable
fun PalettePreview(){
    Palette()
}
