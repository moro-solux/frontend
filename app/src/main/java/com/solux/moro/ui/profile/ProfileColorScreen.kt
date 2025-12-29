package com.solux.moro.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.component.TopBar
import com.solux.moro.core.designsystem.theme.MoroTheme
import com.solux.moro.core.designsystem.theme.MoroThemeType
import com.solux.moro.core.designsystem.theme.colorsOf
import com.solux.moro.ui.profile.component.ColorCell
import com.solux.moro.ui.profile.component.ColorCellData

@Composable
fun ProfileColorScreen(
    viewModel: ProfileColorViewModel = viewModel(),
    modifier: Modifier = Modifier,
    color: Color = MoroTheme.colors.fontColor,
    style: TextStyle = MoroTheme.typography.titleBold24,
    ) {
    Scaffold(
        bottomBar = { BottomBar() },
        topBar = { TopBar() }
    ) { innerPadding ->
        val selectedTheme by viewModel.selectedTheme.collectAsState()
        val colors by viewModel.colors.collectAsState()

        val colorCells = remember(colors) {
            colors.map { color ->
                ColorCellData(
                    color = color,
                    isSelected = false
                )
            }
        }

        Column(Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF121212))
            .padding(innerPadding)) {
            ColorGrid(colorCells)
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
    ColorCell(cell.color,cell.isSelected)
}

@Preview()
@Composable
fun ColorGridPreview() {
    val colors = colorsOf(MoroThemeType.Pastel)

    val colorCells = colors.map {
        ColorCellData(
            color = it,
            isSelected = if(colors.indexOf(it)==10) true else false
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
