package com.solux.moro.components.top
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solux.moro.R
import com.solux.moro.components.top.figmaDp
import java.time.format.TextStyle

private const val TARGET_SCALE = 2.88f


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarBack(
    title: String = "텍스트 입력",
    onBackClick: () -> Unit = {}
) {

    Column {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(color = Color(0xFF121212))
                .padding(start = 16.dp, top = 5.dp, end = 16.dp, bottom = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.left),
                contentDescription = "image description",
            )
            Text(
                text = title,

                // Subtitle2/SemiBold/18px
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 25.2.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                )
            )
            Row(
                modifier = Modifier
                    .width(20.99063.dp)
                    .height(18.dp),
                horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Child views.
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
fun TopBarBackPreview() {
    TopBarBack("안녕"
    )
}
