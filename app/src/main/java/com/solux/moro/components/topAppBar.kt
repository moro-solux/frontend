package com.solux.moro.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solux.moro.R

const val PIXEL_4A_DENSITY = 2.75f
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    Column {
        TopAppBar(
            windowInsets = WindowInsets(0),
            modifier = Modifier
                .background(color = Color(0xFF121212))
                .padding(
                    start = (46.08 / PIXEL_4A_DENSITY).dp,
                    top = (14.4 / PIXEL_4A_DENSITY).dp,
                    end = (46.08 / PIXEL_4A_DENSITY).dp,
                    bottom = (14.4 / PIXEL_4A_DENSITY).dp
                ),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF121212),
                titleContentColor = Color(0xFFF2F2F2),
                actionIconContentColor = Color(0xFFF2F2F2)
            ),

            title = {
                Text(
                    text = "Moro",
                    style = TextStyle(
                        fontSize = (92.16 / PIXEL_4A_DENSITY).sp,
                        lineHeight = (69.12 / PIXEL_4A_DENSITY).sp,
//                    fontFamily = FontFamily(Font(R.font.kit_rounded)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFFF2F2F2),
                        textAlign = TextAlign.Center,
                    ),
                )
            },
            actions = {
                IconButton(
                    onClick = { },
                    Modifier
                        .width((126.72 / PIXEL_4A_DENSITY).dp)
                        .height((126.72 / PIXEL_4A_DENSITY).dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_bell),
                        modifier = Modifier
                            .padding((0.15429 / PIXEL_4A_DENSITY).dp)
                            .width((60.48 / PIXEL_4A_DENSITY).dp)
                            .height((69.12 / PIXEL_4A_DENSITY).dp),
                        contentDescription = "More"
                    )
                }
            },
        )
        HorizontalDivider(
            thickness = (2.88 / PIXEL_4A_DENSITY).dp,
            color = Color(0xFFF2F2F2)
        )
    }
}

@Preview (device = Devices.PIXEL_4A)
@Composable
fun TopBarPreview(){
    com.solux.moro.components.TopBar()
}