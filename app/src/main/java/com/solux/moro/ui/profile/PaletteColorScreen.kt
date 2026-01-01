package com.solux.moro.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.component.TopBar
import com.solux.moro.core.designsystem.theme.Gray40
import com.solux.moro.core.designsystem.theme.MoroPalette
import com.solux.moro.core.designsystem.theme.MoroTheme
import com.solux.moro.ui.profile.component.ColorCellData
import com.solux.moro.ui.profile.component.ColorGrid


@Composable
fun PaletteColorScreen(
    viewModel: PaletteColorViewModel = viewModel(),
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

        val selectedColors by viewModel.selectedColors.collectAsState()
        val editingColorIndex by viewModel.editingColorIndex.collectAsState()
        val editingColor =selectedColors[editingColorIndex]

        Column(Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF121212))
            .padding(innerPadding)) {
            SelectedColorRow(
                colors = selectedColors,
                editingColor=editingColor
            )
            ColorGrid(colorCells)
            SelectedButton()
        }

    }
}

@Composable
fun SelectedColorRow(
    colors: List<Color>,
    editingColor: Color,
    modifier: Modifier = Modifier,
    color: Color = MoroTheme.colors.fontColor,
    style: TextStyle = MoroTheme.typography.titleBold24,
) {
    Column(
        modifier=modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .padding(top = 6.dp)
    ) {
        Text("Palette",
            color=color,
            style=style,
            modifier=modifier
                .padding(horizontal = 20.dp)
                .padding(vertical = 10.dp)
        )
        Row(
            modifier
                .fillMaxWidth()
                .padding(16.dp)
            ,
            Arrangement.SpaceBetween
        ) {
            colors.forEach { color ->
                SelectedColorCell(color = color, editingColor = editingColor)
            }
        }
    }
}

@Composable
fun SelectedColorCell(color: Color,
                      editingColor: Color,
                      modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .size(55.dp)
            .background(color, shape = RoundedCornerShape(30.dp))
            .border(
                width =if(editingColor==color)3.dp else 1.dp,
                color =if(editingColor==color||color== Color.Black) Color.White else Color.Black,
                shape = RoundedCornerShape(30.dp)
            )
    )
}

@Composable
fun SelectedButton(modifier: Modifier = Modifier,
                   color: Color = Color.Black,
                   style: TextStyle = MoroTheme.typography.bodyRegular23,){
    Row(modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center) {
        IconButton(
            onClick = {},
            Modifier.size(50.dp),
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                tint = Color.White,
                modifier = Modifier.size(35.dp),
                contentDescription = "KeyboardArrowLeft icon button"
            )
        }
        Button(
            onClick = {},
            Modifier
                .height(55.dp)
                .width(90.dp),
            shape = RoundedCornerShape(10.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Gray40,
            )
        ) {
            Text(
                text = "저장",
                color = color,
                style = style
            )
        }
        IconButton(
            onClick = {},
            Modifier.size(50.dp)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                tint = Color.White,
                modifier = Modifier.size(35.dp),
                contentDescription = "KeyboardArrowRight icon button"
            )
        }
    }
}

@Preview
@Composable
fun SelectedButtonPreview(){
    SelectedButton()
}

@Preview
@Composable
fun SelectedColorCellPreview(){
    SelectedColorCell(color = Color.Red, editingColor = Color.Red)
}

@Preview(device = Devices.PIXEL_4A)
@Composable
fun SelectedColorRowPreview(){
    SelectedColorRow(
        listOf(
            MoroPalette.Pastel.Purple400,
            MoroPalette.Pastel.Yellow300,
            MoroPalette.Pastel.Green200,
            MoroPalette.Pastel.Cyan200,
            MoroPalette.Pastel.Indigo500,
            MoroPalette.Pastel.Gray400
        ),
        editingColor = MoroPalette.Pastel.Cyan200
    )
}