package com.solux.moro.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.solux.moro.R

@Composable
fun BottomBar() {
    Column {
        HorizontalDivider(
            thickness = (2.88 ).toPxDp,
            color = Color(0xFFF2F2F2)
        )
        BottomAppBar(
            containerColor = Color(0xFF121212),
            windowInsets = WindowInsets(0),
    ) {
            Column(
                Modifier
                    .width((203.60001 ).toPxDp)
                    .height((166.72 ).toPxDp)
                    .background(color = Color(0xFF121212)),
                verticalArrangement = Arrangement.spacedBy(
                    (11.520000457763672 ).toPxDp,
                    Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Icon(
                    painter = painterResource(R.drawable.icon_home),
                    modifier = Modifier
                        .padding((0.15429 ).toPxDp)
                        .width((60.48 ).toPxDp)
                        .height((69.12 ).toPxDp),
                    contentDescription = "home icon",
                    tint = White
                )
                Text(
                    text = "Home",
                    style = TextStyle(
                        fontSize = (40.32 ).toPxSp,
                        lineHeight = (40.32 ).toPxSp,
//                           fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFF2F2F2),

                        textAlign = TextAlign.Center,
                    )
                )
            }

            Column(
                Modifier
                    .width((201.60001 ).toPxDp)
                    .height((166.72 ).toPxDp),
                verticalArrangement = Arrangement.spacedBy(
                    (11.520000457763672 ).toPxDp,
                    Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    painter = painterResource(R.drawable.mission),
                    modifier = Modifier
                        .padding((0.15429 ).toPxDp)
                        .width((60.48 ).toPxDp)
                        .height((69.12 ).toPxDp),
                    contentDescription = "mission icon",
                    tint = White
                )
                Text(
                    text = "Mission",
                    style = TextStyle(
                        fontSize = (40.32 ).toPxSp,
                        lineHeight = (40.32 ).toPxSp,
//                           fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFF2F2F2),

                        textAlign = TextAlign.Center,
                    )
                )
            }
            Column(
                Modifier
                    .width((201.60001 ).toPxDp)
                    .height((165.64 ).toPxDp),
                verticalArrangement = Arrangement.spacedBy(
                    (11.520000457763672 ).toPxDp,
                    Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_camera),
                    modifier = Modifier
                        .padding((0.15429 ).toPxDp)
                        .width((60.48 ).toPxDp)
                        .height((69.12 ).toPxDp),
                    contentDescription = "camera icon",
                    tint = White
                )
                Text(
                    text = "Camera",
                    style = TextStyle(
                        fontSize = (40.32 ).toPxSp,
                        lineHeight = (40.32 ).toPxSp,
//                           fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFF2F2F2),

                        textAlign = TextAlign.Center,
                    )
                )
            }
            Column(
                Modifier
                    .width((201.60001 ).toPxDp)
                    .height((166.72 ).toPxDp),
                verticalArrangement = Arrangement.spacedBy(
                    (11.520000457763672 ).toPxDp,
                    Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    painter = painterResource(R.drawable.map),
                    modifier = Modifier
                        .padding((0.15429 ).toPxDp)
                        .width((60.48 ).toPxDp)
                        .height((69.12 ).toPxDp),
                    contentDescription = "map icon",
                    tint = White
                )
                Text(
                    text = "Map",
                    style = TextStyle(
                        fontSize = (40.32 ).toPxSp,
                        lineHeight = (40.32 ).toPxSp,
//                           fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFF2F2F2),

                        textAlign = TextAlign.Center,
                    )
                )
            }
            Column(
                Modifier
                    .width((201.60001 ).toPxDp)
                    .height((166.72 ).toPxDp),
                verticalArrangement = Arrangement.spacedBy(
                    (11.520000457763672 ).toPxDp,
                    Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    painter = painterResource(R.drawable.profile),
                    modifier = Modifier
                        .padding((0.15429 ).toPxDp)
                        .width((60.48 ).toPxDp)
                        .height((69.12 ).toPxDp),
                    contentDescription = "profile icon",
                    tint = White
                )
                Text(
                    text = "Profile",
                    style = TextStyle(
                        fontSize = (40.32 ).toPxSp,
                        lineHeight = (40.32 ).toPxSp,
//                           fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFF2F2F2),

                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
    }
}


@Preview (
    device = Devices.PIXEL_4A
)
@Composable
fun BottomBarPreview(){
    com.solux.moro.components.BottomBar()
}