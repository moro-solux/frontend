package com.solux.moro.core.designsystem.component.top

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.solux.moro.R
import com.solux.moro.core.util.figmaDp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarBack(
    title: String = "텍스트 입력",
    navigationIconResId: Int = R.drawable.left,
    showBottomDivider: Boolean = true,
    onBackClick: () -> Unit = {}
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(figmaDp(70f))
                .background(color = Color(0xFF121212))
                .padding(horizontal = figmaDp(16f), vertical = figmaDp(5f)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = navigationIconResId),
                contentDescription = "Back Button",
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onBackClick
                    )
                    .padding(figmaDp(4f))
            )

            Text(
                text = title,
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 25.2.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                )
            )


            Box(modifier = Modifier.width(figmaDp(24f)))
        }

        if (showBottomDivider) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(figmaDp(1f))
                    .background(Color(0xFFF2F2F2))
            )
        }
    }
}

@Preview
@Composable
fun TopBarBackPreview() {
    TopBarBack("안녕"
    )
}

@Preview
@Composable
fun TopBarClosePreview() {
    TopBarBack(
        title = "닫기",
        navigationIconResId = R.drawable.ic_close,
        showBottomDivider = false
    )
}
