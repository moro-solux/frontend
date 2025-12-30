package com.solux.moro.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.component.top.TopBarBack
import com.solux.moro.core.util.figmaDp

@Composable
fun SelectedColorScreen() {
    Scaffold(
        topBar = { TopBarBack("컬러맵") },
        bottomBar = { BottomBar() }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .background(Color(0xFF121212))
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(figmaDp(14f)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                SelectedColorSection()
            }
            item {
                ImageSection()
            }
        }
    }
}

@Composable
fun SelectedColorSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(figmaDp(169f)),
        horizontalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .width(figmaDp(80f))
                .height(figmaDp(114f)),
            verticalArrangement = Arrangement.spacedBy(figmaDp(9f), Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                Modifier
                    .shadow(
                        elevation = figmaDp(20f),
                        spotColor = Color(0x66FF6584),
                        ambientColor = Color(0x66FF6584)
                    )
                    .border(
                        width = figmaDp(2f),
                        color = Color(0x4DFFFFFF),
                        shape = RoundedCornerShape(size = figmaDp(9999f))
                    )
                    .size(figmaDp(80f))
                    .background(
                        color = Color(0xFFFF6584),
                        shape = RoundedCornerShape(size = figmaDp(9999f))
                    )
            )
            Row(
                modifier = Modifier
                    .width(figmaDp(80f))
                    .height(figmaDp(25f)),
                horizontalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "#FF6584",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        //fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFF6584),
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }

    }
}

@Composable
fun ImageItem() {
    Box(
        modifier = Modifier
            .size(figmaDp(110.66666f))
            .background(
                color = Color(0xFFA5A5A5),
                shape = RoundedCornerShape(figmaDp(9.22222f))
            )
    )
}

@Composable
fun ImageRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(figmaDp(110.66666f))
            .padding(horizontal = figmaDp(16f)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(3) {
            ImageItem()
        }
    }
}

@Composable
fun ImageSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(figmaDp(575.33331f)),
        verticalArrangement = Arrangement.spacedBy(figmaDp(5.5f))
    ) {
        repeat(3) {
            ImageRow()
        }
    }
}

@Preview
@Composable
fun SelectedColorScreenPreview() {
    SelectedColorScreen()
}
