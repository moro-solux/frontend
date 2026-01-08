package com.solux.moro.ui.ProfileColorEdit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.solux.moro.R
import com.solux.moro.components.BackNavigationTopAppBar
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.theme.Gray40
import com.solux.moro.core.designsystem.theme.MoroTheme
import com.solux.moro.ui.profile.component.ColorCellData
import com.solux.moro.ui.profile.component.ColorGrid

@Composable
fun ProfileColorEditScreen(
    viewModel: ProfileColorEditViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    color: Color = MoroTheme.colors.fontColor,
    style: TextStyle = MoroTheme.typography.titleBold24,
    ) {
    Scaffold(
        bottomBar = { BottomBar() },
        topBar = { BackNavigationTopAppBar(
            "프로필 편집",
            onBackClick = {}
        )}
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

            SelectedThemeRow()
            ColorGrid(colorCells)

            Row(modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center) {

                Button(
                    onClick = {
                        viewModel::updateUserColor
                    },
                    Modifier
                        .height(55.dp)
                        .width(90.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Gray40,
                    )
                ) {
                    Text(
                        text = "저장",
                        color = Color.Black,
                        style = MoroTheme.typography.bodyRegular23
                    )
                }

            }
        }


    }
}

@Composable
fun SelectedThemeRow(
    modifier: Modifier = Modifier,
    color: Color = MoroTheme.colors.fontColor,
    style: TextStyle = MoroTheme.typography.titleBold24,
) {
    Column(
        modifier=modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        Text("Color Map",
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
            Arrangement.Start
        ) {
            Icon(painterResource(
                R.drawable.icon_theme_vivid),
                    contentDescription = "Vivid",
                modifier.size(55.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(painterResource(
                R.drawable.icon_theme_nature),
                contentDescription = "Nature",
                modifier.size(55.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(painterResource(
                R.drawable.icon_theme_pastel),
                contentDescription = "Pastel",
                modifier.size(55.dp),
                tint = Color.Unspecified
            )
        }
    }
}
@Preview(device = Devices.PIXEL_4A)
@Composable
fun SelectedThemeRowPreview(){
    SelectedThemeRow(
    )
}
