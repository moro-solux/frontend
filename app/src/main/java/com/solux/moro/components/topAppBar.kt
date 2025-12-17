package com.solux.moro.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solux.moro.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(){
    TopAppBar(
        modifier =Modifier
            .border(width = 2.88.dp, color = Color(0xFFF2F2F2))
            .width(1080.dp)
            .height(201.60001.dp)
            .background(color = Color(0xFF121212))
            .padding(start = 46.08.dp, top = 14.4.dp, end = 46.08.dp, bottom = 14.4.dp),

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF121212),
            titleContentColor = Color(0xFFF2F2F2),
            actionIconContentColor = Color(0xFFF2F2F2)
        ),
        title = {
            Row(modifier = Modifier.fillMaxHeight()
                ,verticalAlignment = Alignment.CenterVertically,) {
                Text(
                    text = "Moro",
                    style = TextStyle(
                        fontSize = 92.16.sp,
                        lineHeight = 69.12.sp,
//                    fontFamily = FontFamily(Font(R.font.kit_rounded)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFFF2F2F2),
                        textAlign = TextAlign.Center,
                    ),
                )
            }
        },
        actions = {
            Row(modifier = Modifier
                .border(width = 0.dp, color = Color(0xFFF2F2F2))
                .width(126.72.dp)
                .fillMaxHeight()
                ,verticalAlignment = Alignment.CenterVertically,) {
                IconButton(onClick = { },
                    Modifier
                        .border(width = 0.dp, color = Color(0xFFF2F2F2))
                        .width(126.72.dp)
                        .height(126.72.dp)) {
                    Icon(
                        painter = painterResource(R.drawable.bell),
                        modifier = Modifier
                            .padding(0.15429.dp)
                            .width(60.48.dp)
                            .height(69.12.dp),
                        contentDescription = "More"
                    )
                }
            }
        }
    )

}

@Preview (heightDp = 202,
    widthDp = 1080,)
@Composable
fun TopBarPreview(){
    com.solux.moro.components.TopBar()
}