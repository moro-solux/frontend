package com.solux.moro.ui.camera


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.solux.moro.R
import com.solux.moro.core.util.figmaDp

data class PlaceData(
    val name: String,
    val address: String
)
@Composable
fun LocationBottomSheet(
    onClose: () -> Unit,
    onPlaceSelected: (String) -> Unit,
    selectedLocation: String, // [2] 현재 선택된 위치 이름 (체크 표시용)
    nearbyPlaces: List<PlaceData> = listOf( // [3] 가짜 데이터 (나중에 백엔드 데이터로 교체될 곳)
        PlaceData("숙명여자대학교", "Seoul, South Korea"),
        PlaceData("N서울타워", "Seoul, South Korea"),
        PlaceData("경복궁", "Seoul, South Korea"),
        PlaceData("북촌한옥마을", "Seoul, South Korea"),
        PlaceData("명동성당", "Seoul, South Korea")
    )
) {
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
        //위치
        Column(
            verticalArrangement = Arrangement.spacedBy(figmaDp(0f), Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .width(figmaDp(377f))
                    .height(figmaDp(36f))
                    .padding(
                        start = figmaDp(16f),
                        top = figmaDp(16f),
                        end = figmaDp(16f),
                        bottom = figmaDp(16f)
                    ),
                verticalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .border(
                            width = figmaDp(0f),
                            color = Color(0xFFE5E7EB),
                            shape = RoundedCornerShape(size = figmaDp(9999f))
                        )
                        .width(figmaDp(41f))
                        .height(figmaDp(4f))
                        .background(
                            color = Color(0xFFA5A5A5),
                            shape = RoundedCornerShape(size = figmaDp(9999f))
                        )
                        .padding(
                            start = figmaDp(8f),
                            top = figmaDp(8f),
                            end = figmaDp(8f),
                            bottom = figmaDp(8f)
                        ),
                    horizontalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Start),
                    verticalAlignment = Alignment.Top,
                ) {
                }
            }

            //위치+취소
            Row(
                modifier = Modifier
                    .width(figmaDp(375f))
                    .height(figmaDp(28f))
                    .padding(
                        start = figmaDp(20f),
                        end = figmaDp(20f)
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {

                // 왼쪽
                Box(
                    modifier = Modifier
                        .width(figmaDp(30f))
                        .height(figmaDp(28f)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(figmaDp(22f)),
                        painter = painterResource(id = R.drawable.map),
                        contentDescription = "map"
                    )
                }

                // 텍스트
                Text(
                    modifier = Modifier.height(figmaDp(28f)),
                    text = "위치",
                    fontSize = 18.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF2F2F2),
                    textAlign = TextAlign.Center,
                )

                // 오른쪽
                Box(
                    modifier = Modifier
                        .width(figmaDp(30f))
                        .height(figmaDp(28f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.clickable { onClose() },
                        text = "취소",
                        fontSize = 16.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF2F2F2),
                        textAlign = TextAlign.Center,
                    )
                }
            }

            //search
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
                        .padding(
                            start = figmaDp(16f),
                            top = figmaDp(4f),
                            end = figmaDp(16f),
                            bottom = figmaDp(4f)
                        ),
                    horizontalArrangement = Arrangement.spacedBy(figmaDp(0f), Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        modifier = Modifier
                            .width(figmaDp(311f))
                            .height(figmaDp(40f)),
                        horizontalArrangement = Arrangement.spacedBy(figmaDp(0f), Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            modifier = Modifier
                                .width(figmaDp(16f))
                                .height(figmaDp(16f)),
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = Color.White
                        )

                        Row(
                            modifier = Modifier
                                .width(figmaDp(295f))
                                .height(figmaDp(40f))
                                .padding(
                                    start = figmaDp(8f),
                                    top = figmaDp(8f),
                                    end = figmaDp(8f),
                                    bottom = figmaDp(8f)
                                ),
                            horizontalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Start),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Search location",
                                style = androidx.compose.ui.text.TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp,
                                    //fontFamily = FontFamily(Font(R.font.inter)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFFD5D5D5),
                                )
                            )
                        }
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(figmaDp(12f)),
                contentPadding = PaddingValues(vertical = figmaDp(8f))
            ) {
                // items()를 사용하여 리스트만큼 반복 생성
                items(nearbyPlaces) { place ->
                    PlaceItem(
                        // 현재 이 아이템의 이름이 선택된 이름과 같으면 체크 표시
                        isSelected = (place.name == selectedLocation),
                        name = place.name,
                        address = place.address,
                        onClick = { onPlaceSelected(place.name) } // 클릭 시 이름 전달
                    )
                }
            }

            Column(
                modifier = Modifier
                    .width(figmaDp(375f))
                    .height(figmaDp(76f))
                    .padding(
                        start = figmaDp(16f),
                        top = figmaDp(16f),
                        end = figmaDp(16f),
                        bottom = figmaDp(16f)
                    ),
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
                        )
                        .padding(
                            start = figmaDp(12f),
                            top = figmaDp(12f),
                            end = figmaDp(12f),
                            bottom = figmaDp(12f)
                        ),
                    horizontalArrangement = Arrangement.spacedBy(figmaDp(12f), Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        modifier = Modifier
                            .width(figmaDp(55f))
                            .height(figmaDp(20f)),
                        horizontalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "위치 추가",

                            // Body2/Regular/14px
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 19.6.sp,
                                //fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFFF2F2F2),
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlaceItem(
    isSelected: Boolean,
    name: String,
    address: String,
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
                    address = address,
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
    address: String,
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
                text = address,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF525252)
            )
        }
    }
}

@Preview
@Composable
fun LocationBottomSheetPreview() {
    //LocationBottomSheet()
}
