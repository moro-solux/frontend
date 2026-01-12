package com.solux.moro.ui.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solux.moro.core.designsystem.theme.MoroThemeType
import com.solux.moro.core.designsystem.theme.colorsOf
import com.solux.moro.core.util.figmaDp

data class ColorCellData(
    val color: Color,
    val isSelected: Boolean,
    val isLocked: Boolean = false
)
@Composable
fun ColorCell(
    color: Color,
    isLocked: Boolean = false,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(50.dp)
            .background(color, shape = RoundedCornerShape(8.dp))
            .border(
                width = if (isSelected) 3.dp else 1.dp,
                color = if (isSelected || color == Color.Black) Color.White else Color.Black,
                shape = RoundedCornerShape(8.dp)
            )
    )

    if (isLocked) {
        Box(
            modifier = modifier
                .size(50.dp)
                .background( Color(0x80000000), shape = RoundedCornerShape(8.dp))
                .border(
                    width = if (isSelected) 3.dp else 1.dp,
                    color = if (isSelected || color == Color.Black) Color.White else Color.Black,
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                tint = Color(0xFF2E2E2E),
                modifier = Modifier.size(figmaDp(16f))
            )
        }
    }


}

@Composable
fun ColorGrid(
    colorCells: List<ColorCellData>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(6),
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp)
    ) {items(
        items = colorCells,
        key = { it.color.toArgb() }
    ) { cell ->
        ColorCellItem(cell)
    }
    }
}

@Composable
fun ColorCellItem(cell: ColorCellData) {
    ColorCell(color=cell.color,isLocked = cell.isLocked, isSelected = cell.isSelected)
}

@Preview()
@Composable
fun ColorGridPreview() {
    val colors = colorsOf(MoroThemeType.Pastel)

    val colorCells = colors.map {
        ColorCellData(
            color = it,
            isSelected = if(colors.indexOf(it)==10) true else false,
            isLocked = if(colors.indexOf(it)==11) true else false
        )
    }

    ColorGrid(colorCells = colorCells)
}

@Preview
@Composable
fun ColorCellItemPreview() {
    ColorCellItem(
        cell = ColorCellData(
            color = Color(0xFFF5D8E0),
            isSelected = true
        )
    )
}

@Preview()
@Composable
fun ColorCellPreview(){
    ColorCell(Color.Red, true,true)
}