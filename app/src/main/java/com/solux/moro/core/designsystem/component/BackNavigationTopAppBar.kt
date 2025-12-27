package com.solux.moro.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.solux.moro.R
import com.solux.moro.ui.profile.component.toPxDp
import com.solux.moro.ui.profile.component.toPxSp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackNavigationTopAppBar(  title: String ?= "colornoonsong",
                   onBackClick: () -> Unit,) {
<<<<<<< Updated upstream:app/src/main/java/com/solux/moro/core/designsystem/component/FollowTopBar.kt
    Column {
        Row(
=======
    Column() {
        Row(
            modifier = Modifier
                .height(76.57.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.padding(0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_back),
                    contentDescription = "뒤로가기",
                    tint = (Color(0xFFFFFFFF)),
                    modifier = Modifier.padding(0.dp),
                )
            }
            Row(
                modifier = Modifier
                    .padding(end = 50.dp),
                    //.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title ?: "colornoonsong",
                    style = TextStyle(
                        fontSize = (51.84).toPxSp,
                        lineHeight = (69.12).toPxSp,
//                    fontFamily = FontFamily(Font(R.font.kit_rounded)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFFF2F2F2),
                        textAlign = TextAlign.Center,
                    ),
                )
            }
            Spacer(modifier = Modifier.height(76.57.dp))
        }
        HorizontalDivider(
            thickness = (2.88).toPxDp,
            color = Color(0xFFF2F2F2)
        )
    }
    /*   Column {
        TopAppBar(
            windowInsets = WindowInsets(0),
>>>>>>> Stashed changes:app/src/main/java/com/solux/moro/core/designsystem/component/BackNavigationTopAppBar.kt
            modifier = Modifier
                .fillMaxWidth()
                //.width(1080.toPxDp)
                .height(201.60001.toPxDp)
                .padding(
                    start = 13.2.toPxDp,
                    top = 14.4.toPxDp,
                    end = 46.08.toPxDp,
                    bottom = 14.4.toPxDp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_back),
                    contentDescription = "뒤로가기",
                    tint = Color.White,
                    modifier = Modifier
                        //.padding((0.15429).toPxDp)
                        //.scale(1.3f)
                        .width((60.48).toPxDp)
                        .height((69.12).toPxDp)
                )
            }
            Text(
                text = title ?: "colornoonsong",
                style = TextStyle(
                    fontSize = (51.84).toPxSp,
                    lineHeight = (69.12).toPxSp,
//                    fontFamily = FontFamily(Font(R.font.kit_rounded)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFFF2F2F2),
                    textAlign = TextAlign.Center,
                ),
            )
            Spacer(modifier = Modifier.width(60.45301.toPxDp))
        }
        HorizontalDivider(
            thickness = (2.88).toPxDp,
            color = Color(0xFFF2F2F2)
        )
    }

  */
}

@Preview()
@Composable
fun BackNavigationTopAppBarPreview(){
    com.solux.moro.components.BackNavigationTopAppBar(null,{})
}