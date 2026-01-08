package com.solux.moro.ui.map.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.solux.moro.R
import com.solux.moro.core.designsystem.theme.MoroPalette
import com.solux.moro.core.designsystem.theme.MoroTheme
import com.solux.moro.data.dto.response.MapPostDetailDto

@Composable
fun MapPostBottomSheet(
    detail: MapPostDetailDto,
    modifier: Modifier = Modifier,
) {
    val imageSize = 120.dp

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(imageSize)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF2A2A2A)),
                contentAlignment = Alignment.Center
            ) {
                if (!detail.imageUrl.isNullOrBlank()) {
                    AsyncImage(
                        model = detail.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(imageSize),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF374151)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_location2),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = detail.title,
                            style = MoroTheme.typography.bodyRegular14,
                            color = MoroTheme.colors.fontColor
                        )
                        Text(
                            text = detail.address,
                            color = Color.White.copy(alpha = 0.55f),
                            fontSize = 10.sp
                        )
                    }
                }

                Column {
                    Text(
                        text = detail.date,
                        style = MoroTheme.typography.bodyRegular12,
                        color = MoroPalette.Pastel.Gray400
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        detail.colors.take(4).forEach { hex ->
                            Text(
                                text = hex.uppercase(),
                                color = MoroPalette.Pastel.Gray400,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }
    }
}