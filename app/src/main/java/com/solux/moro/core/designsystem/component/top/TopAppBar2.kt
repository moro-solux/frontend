package com.solux.moro.core.designsystem.component.top

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.solux.moro.R
import com.solux.moro.core.util.figmaDp

@Composable
fun MoroTopBarIcon(
    iconResId: Int,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .width(figmaDp(44f))
            .height(figmaDp(44f)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            contentScale = ContentScale.None
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar2(
    actions: @Composable RowScope.() -> Unit = {}
) {

    Column {

    
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(figmaDp(70f))
            .background(color = Color(0xFF121212))
            .padding(
                start = figmaDp(16f),
                top = figmaDp(5f),
                end = figmaDp(16f),
                bottom = figmaDp(5f)
            )
    ) {
        Image(
            modifier = Modifier
                .width(figmaDp(82f))
                .height(figmaDp(24f)),
            painter = painterResource(id = R.drawable.moro_logo),
            contentDescription = null
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(figmaDp(0f), Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            actions()
        }

    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(figmaDp(1f))
            .background(Color(0xFFF2F2F2))
    )
        }

}

@Preview
@Composable
fun TopBarPreview() {
    TopBar2(
        actions = {
            MoroTopBarIcon(iconResId = R.drawable.icon_bell)
            MoroTopBarIcon(iconResId = R.drawable.icon_bell)
        }
    )
}
