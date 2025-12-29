package com.solux.moro.ui.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
data class ColorCellData(
    val color: Color,
    val isSelected: Boolean
)
@Composable
fun ColorCell(
    color: Color,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(50.dp)
            .background(color, shape = RoundedCornerShape(8.dp))
            .border(
                width = if (isSelected) 3.dp else 1.dp,
                color =if (isSelected||color==Color.Black) Color.White else Color.Black,
                shape = RoundedCornerShape(8.dp)
            )
    )
}

@Preview()
@Composable
fun ColorCellPreview(){
    ColorCell(Color.Red, true)
}