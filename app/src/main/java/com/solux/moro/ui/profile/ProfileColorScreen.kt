package com.solux.moro.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.component.TopBar
import com.solux.moro.core.designsystem.theme.MoroTheme
import com.solux.moro.ui.profile.component.ColorCellData
import com.solux.moro.ui.profile.component.ColorGrid

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

