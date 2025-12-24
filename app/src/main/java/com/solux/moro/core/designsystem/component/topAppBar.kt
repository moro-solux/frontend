package com.solux.moro.core.designsystem.component

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
import com.solux.moro.R
import com.solux.moro.ui.profile.component.toPxDp
import com.solux.moro.ui.profile.component.toPxSp

const val PIXEL_DENSITY = 2.625f
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    Column {
        TopAppBar(
            windowInsets = WindowInsets(0),
            modifier = Modifier
                .background(color = Color(0xFF121212))
                .padding(
                    start = (46.08).toPxDp,
                    top = (14.4).toPxDp,
                    end = (46.08).toPxDp,
                    bottom = (14.4).toPxDp
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
                        fontSize = (92.16).toPxSp,
                        lineHeight = (69.12).toPxSp,
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
                        .width((126.72).toPxDp)
                        .height((126.72).toPxDp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_bell),
                        modifier = Modifier
                            .padding((0.15429).toPxDp)
                            .width((60.48).toPxDp)
                            .height((69.12).toPxDp),
                        contentDescription = "More"
                    )
                }
            },
        )
        HorizontalDivider(
            thickness = (2.88).toPxDp,
            color = Color(0xFFF2F2F2)
        )
    }
}

@Preview (device = Devices.PIXEL_4A)
@Composable
fun TopBarPreview(){
    TopBar()
}