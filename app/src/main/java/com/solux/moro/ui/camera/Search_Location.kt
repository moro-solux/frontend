package com.solux.moro.ui.camera

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.solux.moro.R
import com.solux.moro.core.util.figmaDp


data class PlaceData(
    val name: String,
    val placeName: String,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val placeId: String? = null
)

@Composable
fun LocationBottomSheet(
    onClose: () -> Unit,
    onPlaceSelected: (PlaceData) -> Unit,
    onSearch: (String) -> Unit,
    selectedLocation: String,
    nearbyPlaces: List<PlaceData> = emptyList()
) {
    // 검색어 상태 관리
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .shadow(
                elevation = figmaDp(50f),
                spotColor = Color(0x40000000),
                ambientColor = Color(0x40000000)
            )
            .width(figmaDp(375f))
            .height(figmaDp(512f))
            .background(
                color = Color(0xFF121212),
                shape = RoundedCornerShape(
                    topStart = figmaDp(24f),
                    topEnd = figmaDp(24f),
                    bottomStart = figmaDp(0f),
                    bottomEnd = figmaDp(0f)
                )
            ),
        verticalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.CenterVertically),
        horizontalAlignment = Alignment.Start,
    ) {
        // 상단 핸들 및 제목 영역
        Column(
            verticalArrangement = Arrangement.spacedBy(figmaDp(0f), Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // 드래그 핸들
            Column(
                modifier = Modifier
                    .width(figmaDp(377f))
                    .height(figmaDp(36f))
                    .padding(figmaDp(16f)),
                verticalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .width(figmaDp(41f))
                        .height(figmaDp(4f))
                        .background(
                            color = Color(0xFFA5A5A5),
                            shape = RoundedCornerShape(size = figmaDp(9999f))
                        )
                )
            }

            // 제목 (위치 + 취소)
            Row(
                modifier = Modifier
                    .width(figmaDp(375f))
                    .height(figmaDp(28f))
                    .padding(horizontal = figmaDp(20f)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // 아이콘
                Box(modifier = Modifier.size(figmaDp(28f)), contentAlignment = Alignment.Center) {
                    Image(
                        modifier = Modifier.size(figmaDp(22f)),
                        painter = painterResource(id = R.drawable.map),
                        contentDescription = "map"
                    )
                }

                // 제목 텍스트
                Text(
                    text = "위치",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF2F2F2),
                    textAlign = TextAlign.Center,
                )

                // 취소 버튼
                Box(modifier = Modifier.size(figmaDp(28f)), contentAlignment = Alignment.Center) {
                    Text(
                        modifier = Modifier.clickable { onClose() },
                        text = "취소",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF2F2F2),
                        textAlign = TextAlign.Center,
                    )
                }
            }

            // 검색창 영역
            Column(
                modifier = Modifier
                    .width(figmaDp(375f))
                    .height(figmaDp(68f))
                    .padding(
                        start = figmaDp(16f),
                        top = figmaDp(10f),
                        end = figmaDp(16f),
                        bottom = figmaDp(10f)
                    ),
                verticalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Top),
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    modifier = Modifier
                        .width(figmaDp(343f))
                        .height(figmaDp(48f))
                        .background(
                            color = Color(0xFF2E2E2E),
                            shape = RoundedCornerShape(size = figmaDp(10f))
                        )
                        .padding(horizontal = figmaDp(16f)),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier.size(figmaDp(16f)),
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )

                    Spacer(modifier = Modifier.width(figmaDp(8f)))


                    BasicTextField(
                        value = searchText,
                        onValueChange = {
                            searchText = it
                            onSearch(it)
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFD5D5D5),
                        ),
                        singleLine = true,
                        cursorBrush = SolidColor(Color.White),
                        decorationBox = { innerTextField ->
                            if (searchText.isEmpty()) {
                                Text(
                                    text = "Search location",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        lineHeight = 24.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFFD5D5D5).copy(alpha = 0.5f),
                                    )
                                )
                            }
                            innerTextField()
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // 리스트 영역
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(figmaDp(12f)),
                contentPadding = PaddingValues(vertical = figmaDp(8f))
            ) {
                items(nearbyPlaces) { place ->
                    PlaceItem(
                        isSelected = (place.name == selectedLocation),
                        name = place.name,
                        placeName = place.placeName,
                        onClick = { onPlaceSelected(place) } // [수정] PlaceData 객체 전달
                    )
                }
            }

            //위치 추가
            Column(
                modifier = Modifier
                    .width(figmaDp(375f))
                    .height(figmaDp(76f))
                    .padding(figmaDp(16f)),
                verticalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Top),
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    modifier = Modifier
                        .border(
                            width = figmaDp(1f),
                            color = Color(0xFFF2F2F2),
                            shape = RoundedCornerShape(size = figmaDp(10f))
                        )
                        .width(figmaDp(343f))
                        .height(figmaDp(44f))
                        .background(
                            color = Color(0xFF171717),
                            shape = RoundedCornerShape(size = figmaDp(10f))
                        ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "위치 추가",
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 19.6.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFF2F2F2),
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun PlaceItem(
    isSelected: Boolean,
    name: String,
    placeName: String,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = figmaDp(16f))
            .clickable { onClick() },
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(
            modifier = Modifier
                .then(
                    if (isSelected) {
                        Modifier.shadow(
                            elevation = figmaDp(4f),
                            spotColor = Color(0x80FFFFFF),
                            ambientColor = Color(0x80FFFFFF)
                        )
                    } else Modifier
                )
                .fillMaxWidth()
                .height(figmaDp(68f))
                .background(
                    color = Color(0xFF121212),
                    shape = RoundedCornerShape(figmaDp(8f))
                )
                .padding(
                    horizontal = figmaDp(18f),
                    vertical = figmaDp(12f)
                ),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PlaceInfo(
                    name = name,
                    placeName = placeName,
                    modifier = Modifier.weight(1f)
                )

                if (isSelected) {
                    Image(
                        modifier = Modifier
                            .width(figmaDp(12f))
                            .height(figmaDp(16f)),
                        painter = painterResource(id = R.drawable.icon_check),
                        contentDescription = "selected"
                    )
                }
            }
        }
    }
}

@Composable
fun PlaceInfo(
    name: String,
    placeName: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(figmaDp(15f)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .width(figmaDp(12f))
                .height(figmaDp(16f)),
            painter = painterResource(id = R.drawable.icon_pin),
            contentDescription = "pin"
        )

        Column {
            Text(
                text = name,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFFD5D5D5)
            )

            Text(
                text = placeName,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF525252)
            )
        }
    }
}