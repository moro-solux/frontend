package com.solux.moro.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.component.top.TopBarBack
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.solux.moro.core.util.figmaDp


data class ColorItem(
    val color: Color,
    val number: String,
    val isLocked: Boolean = false
)


typealias ColorGrid = List<List<ColorItem>>

@Composable
fun ColorMapScreen() {
    var isNumberOn by remember { mutableStateOf(true) }
    var currentTheme by remember {
        mutableStateOf(ColorThemeType.PASTEL)
    }
    val currentGrid = when (currentTheme) {
        ColorThemeType.PASTEL -> pastelColorGrid
        ColorThemeType.VIVID -> vividColorGrid
        ColorThemeType.NATURE -> natureColorGrid
    }

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
                ColorTheme(
                    isOn = isNumberOn,
                    onToggle = { isNumberOn = it },
                    currentTheme = currentTheme,
                    onThemeChange = { currentTheme = it }
                )


                ColorMapSection(
                    colorGrid = currentGrid,
                    showNumber = isNumberOn
                )

            }
        }
    }
}

@Composable
fun Num_Button(
    isOn: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .width(figmaDp(48f))
            .height(figmaDp(46f))
        .clickable { onToggle(!isOn) },
        horizontalAlignment = Alignment.End,
    ) {
        Text(
            modifier = Modifier
                .width(figmaDp(48f))
                .height(figmaDp(22f)),
            text = "num",
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 22.4.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFFF2F2F2),
                textAlign = TextAlign.Center,
            )
        )

        Row(
            modifier = Modifier
                .width(figmaDp(48f))
                .height(figmaDp(24f))
                .background(
                    color = if (isOn) Color(0xFFF2F2F2) else Color(0xFF737373),
                    shape = RoundedCornerShape(figmaDp(9999f))
                )
                .padding(
                    start = figmaDp(if (isOn) 2f else 26f),
                    end = figmaDp(if (isOn) 26f else 2f),
                    top = figmaDp(2f),
                    bottom = figmaDp(2f)
                ),
            horizontalArrangement = if (isOn)
                Arrangement.Start else Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(figmaDp(20f))
                    .clip(CircleShape)
                    .background(
                        color = if (isOn) Color(0xFFBDBDBD) else Color.White
                    )
                    .border(figmaDp(2f), Color(0xFFF2F2F2), CircleShape)
            )
        }
    }
}

@Composable
fun ColorTheme(
    isOn: Boolean,
    onToggle: (Boolean) -> Unit,
    currentTheme: ColorThemeType,
    onThemeChange: (ColorThemeType) -> Unit
)
 {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = figmaDp(16f),
                top = figmaDp(20f),
                end = figmaDp(16f),
                bottom = figmaDp(16f)
            ),
        verticalArrangement = Arrangement.spacedBy(figmaDp(12f), Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .height(figmaDp(28f)),
            text = "Color Map",

            // Subtitle2/SemiBold/18px
            style = TextStyle(
                fontSize = 18.sp,
                lineHeight = 25.2.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFFF2F2F2),
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(figmaDp(48f)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            //컬러맵 테마 종류
            Row(
                modifier = Modifier
                    .width(figmaDp(168f))
                    .height(figmaDp(48f)),
                horizontalArrangement = Arrangement.spacedBy(figmaDp(12f), Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(figmaDp(48f))
                        .clip(CircleShape)
                        .clickable {
                            onThemeChange(ColorThemeType.VIVID)
                        }
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFDE3323),
                                    Color(0xFFE26F2E),
                                    Color(0xFFFCFE57),
                                    Color(0xFF6AC83E)
                                ),
                                start = Offset.Zero,
                                end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                            )
                        )
                        .border(figmaDp(2f), Color(0xFFF2F2F2), CircleShape)
                )
                Box(
                    modifier = Modifier
                        .size(figmaDp(48f))
                        .clip(CircleShape)
                        .clickable {
                            onThemeChange(ColorThemeType.PASTEL)
                        }
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFFF6CB4),
                                    Color(0xFFFFBC4F),
                                    Color(0xFFDEFF9C),
                                    Color(0xFFCAFFC6)
                                ),
                                start = Offset.Zero,
                                end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                            )
                        )
                        .border(figmaDp(2f), Color(0xFFF2F2F2), CircleShape)
                )
                Box(
                    modifier = Modifier
                        .size(figmaDp(48f))
                        .clip(CircleShape)
                        .clickable {
                            onThemeChange(ColorThemeType.NATURE)
                        }
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF8EDBFE),
                                    Color(0xFF9EE2DB),
                                    Color(0xFFF0AA58)
                                ),
                                start = Offset.Zero,
                                end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                            )
                        )
                        .border(figmaDp(2f), Color(0xFFF2F2F2), CircleShape)
                )
            }

            Num_Button(
                isOn = isOn,
                onToggle = onToggle
            )
        }
    }
}
//var isOn by remember { mutableStateOf(true) } 사용 예시
//
//Num_Button(isOn = isOn)
enum class ColorThemeType {
    PASTEL,
    VIVID,
    NATURE
}

val colorRows = listOf(
    listOf(
        ColorItem(Color(0xFFF5D8E0), number = "12"),
        ColorItem(Color(0xFFEEB6C6), number = "8"),
        ColorItem(Color(0xFFE895AB), number = "21"),
        ColorItem(Color(0xFFDF7DA8), number = "5"),
        ColorItem(Color(0xFFCD5F80), number = "17"),
        ColorItem(Color(0xFFB34166), number = "30")
    ),
    listOf(
        ColorItem(Color(0xFFF7E6BA), number = "4"),
        ColorItem(Color(0xFFF3D6A5), number = "9"),
        ColorItem(Color(0xFFEEBB84), number = "14"),
        ColorItem(Color(0xFFE99F65), number = "26"),
        ColorItem(Color(0xFFD1814B), number = "11"),
        ColorItem(Color(0xFFB76B40), number = "19")
    ),
    listOf(
        ColorItem(Color(0xFFFCF9CA), number = "6"),
        ColorItem(Color(0xFFFAF5A8), number = "13"),
        ColorItem(Color(0xFFF9F189), number = "22"),
        ColorItem(Color(0xFFF8EE74), number = "2"),
        ColorItem(Color(0xFFF1D95B), number = "18"),
        ColorItem(Color(0xFFEBC251), number = "27")
    ),
    listOf(
        ColorItem(Color(0xFFD8EFC5), number = "7"),
        ColorItem(Color(0xFFBFE3AA), number = "15"),
        ColorItem(Color(0xFFA8D98F), number = "24"),
        ColorItem(Color(0xFF92CD76), number = "10"),
        ColorItem(Color(0xFF7EC35E), number = "16"),
        ColorItem(Color(0xFF64A743), number = "28")
    ),
    listOf(
        ColorItem(Color(0xFFDEEFFD), number = "3"),
        ColorItem(Color(0xFFC3E4FC), number = "20"),
        ColorItem(Color(0xFFA9D7FB), number = "25"),
        ColorItem(Color(0xFF91CAFA), number = "1"),
        ColorItem(Color(0xFF7CBDF9), number = "23"),
        ColorItem(Color(0xFF7CBDF9), number = "29")
    ),
    listOf(
        ColorItem(Color(0xFFE7E6F8), number = "11"),
        ColorItem(Color(0xFFD2C0D6), number = "6"),
        ColorItem(Color(0xFFC5A6DA), number = "18"),
        ColorItem(Color(0xFFAF84CC), number = "9"),
        ColorItem(Color(0xFF905DB0), number = "27"),
        ColorItem(Color(0xFF744493), number = "14")
    ),
    listOf(
        ColorItem(Color(0xFFF5F5F5), number = "2"),
        ColorItem(Color(0xFFE0E0E0), number = "8"),
        ColorItem(Color(0xFFCCCCCC), number = "16"),
        ColorItem(Color(0xFFB3B3B3), number = "21"),
        ColorItem(Color(0xFF999999), number = "5"),
        ColorItem(Color(0xFF7F7F7F), number = "12")
    ),
    listOf(
        ColorItem(Color(0xFF000000), number = "0", isLocked = true),
        ColorItem(Color(0xFFFFFFFF), number = "7"),
        ColorItem(Color(0xFFDDDDDD), number = "19"),
        ColorItem(Color(0xFF444444), number = "0", isLocked = true),
        ColorItem(Color(0xFFFAF9F6), number = "24"),
        ColorItem(Color(0xFFC0C0C0), number = "10")
    )
)
val pastelColorGrid: ColorGrid = colorRows

val vividColorGrid: ColorGrid = listOf(
    listOf(
        ColorItem(Color(0xFFDE3323), "12"),
        ColorItem(Color(0xFFB1271A), "8"),
        ColorItem(Color(0xFF851A11), "21"),
        ColorItem(Color(0xFF580E08), "5"),
        ColorItem(Color(0xFFE26F2E), "17"),
        ColorItem(Color(0xFFB55923), "30")
    ),
    listOf(
        ColorItem(Color(0xFF874218), "4"),
        ColorItem(Color(0xFF5A2C0D), "9"),
        ColorItem(Color(0xFFFCFE57), "14"),
        ColorItem(Color(0xFFC9CB44), "26"),
        ColorItem(Color(0xFF979831), "11"),
        ColorItem(Color(0xFF65661E), "19")
    ),
    listOf(
        ColorItem(Color(0xFF6AC83E), "6"),
        ColorItem(Color(0xFF4E962C), "13"),
        ColorItem(Color(0xFF32641A), "22"),
        ColorItem(Color(0xFF163209), "2"),
        ColorItem(Color(0xFF4767F5), "18"),
        ColorItem(Color(0xFF2E4C93), "27")
    ),
    listOf(
        ColorItem(Color(0xFF1D3362), "7"),
        ColorItem(Color(0xFF0B1931), "15"),
        ColorItem(Color(0xFF8C25F5), "24"),
        ColorItem(Color(0xFF6A1BC3), "10"),
        ColorItem(Color(0xFF481092), "16"),
        ColorItem(Color(0xFF2F0761), "28")
    ),
    listOf(
        ColorItem(Color(0xFFE04997), "3"),
        ColorItem(Color(0xFFB33A78), "20"),
        ColorItem(Color(0xFF862B5A), "25"),
        ColorItem(Color(0xFF591D3C), "1"),
        ColorItem(Color(0xFF905636), "23"),
        ColorItem(Color(0xFF6D3F27), "29")
    ),
    listOf(
        ColorItem(Color(0xFF4C2A19), "11"),
        ColorItem(Color(0xFF26150C), "6"),
        ColorItem(Color(0xFF6197C7), "18"),
        ColorItem(Color(0xFF487295), "9"),
        ColorItem(Color(0xFF304C64), "27"),
        ColorItem(Color(0xFF172632), "14")
    ),
    listOf(
        ColorItem(Color(0xFF979846), "2"),
        ColorItem(Color(0xFF727334), "8"),
        ColorItem(Color(0xFF4C4D23), "16"),
        ColorItem(Color(0xFF252611), "21"),
        ColorItem(Color(0xFF78C9CB), "5"),
        ColorItem(Color(0xFF599798), "12")
    ),
    listOf(
        ColorItem(Color(0xFF3B6565), "0", isLocked = true),
        ColorItem(Color(0xFF1C3233), "7"),
        ColorItem(Color(0xFF242424), "19"),
        ColorItem(Color(0xFFF7F4F4), "0", isLocked = true),
        ColorItem(Color(0xFF999999), "24"),
        ColorItem(Color(0xFF333333), "10")
    )
)

val natureColorGrid: ColorGrid = listOf(
    listOf(
        ColorItem(Color(0xFFDFF6FD), "12"),
        ColorItem(Color(0xFFCBF2FD), "8"),
        ColorItem(Color(0xFFABE9FE), "21"),
        ColorItem(Color(0xFF8EDBFE), "5"),
        ColorItem(Color(0xFF5ABAF4), "17"),
        ColorItem(Color(0xFF1985C3), "30")
    ),
    listOf(
        ColorItem(Color(0xFFDFF6FC), "4"),
        ColorItem(Color(0xFFD2F2FC), "9"),
        ColorItem(Color(0xFFA2E8FB), "14"),
        ColorItem(Color(0xFF68D3F8), "26"),
        ColorItem(Color(0xFF40B7E7), "11"),
        ColorItem(Color(0xFF0D8ABB), "19")
    ),
    listOf(
        ColorItem(Color(0xFFD7F1F2), "6"),
        ColorItem(Color(0xFFBAEBE6), "13"),
        ColorItem(Color(0xFF9EE2DB), "22"),
        ColorItem(Color(0xFF5BCEC6), "2"),
        ColorItem(Color(0xFF3BB9BF), "18"),
        ColorItem(Color(0xFF10809C), "27")
    ),
    listOf(
        ColorItem(Color(0xFFD2EEEC), "7"),
        ColorItem(Color(0xFFC1E6E0), "15"),
        ColorItem(Color(0xFF8FDBD2), "24"),
        ColorItem(Color(0xFF6AC6BB), "10"),
        ColorItem(Color(0xFF3FB9A5), "16"),
        ColorItem(Color(0xFF11788A), "28")
    ),
    listOf(
        ColorItem(Color(0xFFDBE8D6), "3"),
        ColorItem(Color(0xFFC6D9B7), "20"),
        ColorItem(Color(0xFF99BB7F), "25"),
        ColorItem(Color(0xFF699860), "1"),
        ColorItem(Color(0xFF4C7B4D), "23"),
        ColorItem(Color(0xFF3D6547), "29")
    ),
    listOf(
        ColorItem(Color(0xFFEAE5D7), "11"),
        ColorItem(Color(0xFFE5D6BD), "6"),
        ColorItem(Color(0xFFD3C09C), "18"),
        ColorItem(Color(0xFFB4A17D), "9"),
        ColorItem(Color(0xFF978161), "27"),
        ColorItem(Color(0xFF5F4E3E), "14")
    ),
    listOf(
        ColorItem(Color(0xFFF2DFB6), "2"),
        ColorItem(Color(0xFFF3C386), "8"),
        ColorItem(Color(0xFFF0AA58), "16"),
        ColorItem(Color(0xFFD66D44), "21"),
        ColorItem(Color(0xFFB95145), "5"),
        ColorItem(Color(0xFF884642), "12")
    ),
    listOf(
        ColorItem(Color(0xFFF1CBB0), "0", isLocked = true),
        ColorItem(Color(0xFFEFB481), "7"),
        ColorItem(Color(0xFFE38373), "19"),
        ColorItem(Color(0xFFCE656C), "0", isLocked = true),
        ColorItem(Color(0xFF96547D), "24"),
        ColorItem(Color(0xFF7D466A), "10")
    )
)

@Composable
fun ColorMapSection(
    colorGrid: List<List<ColorItem>>,
    showNumber: Boolean
)
{
    Column(
        modifier = Modifier
            .width(figmaDp(375f))
            .padding(
                start = figmaDp(16f),
                top = figmaDp(20f),
                end = figmaDp(16f),
                bottom = figmaDp(16f)
            ),
        verticalArrangement = Arrangement.spacedBy(figmaDp(8f)),
        horizontalAlignment = Alignment.Start,
    ) {
        colorGrid.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(figmaDp(10f))
            ) {
                row.forEach { item ->
                    ColorCell(
                        color = item.color,
                        isLocked = item.isLocked,
                        showNumber = showNumber,
                        number = item.number
                    )
                }
            }
        }
    }
}


@Composable
fun ColorCell(
    color: Color,
    isLocked: Boolean = false,
    isSelected: Boolean = false,
    showNumber: Boolean = false,
    number: String = "12"
) {
    val isDarkColor = color.luminance() < 0.15f
    val shape = RoundedCornerShape(figmaDp(9.47514f))

    Box(
        modifier = Modifier
            .size(figmaDp(48.83333f))
            .clip(shape)
            .background(color)
            .then(
                if (isDarkColor)
                    Modifier.border(
                        figmaDp(1f),
                        Color(0xFF404040),
                        shape
                    )
                else Modifier
            )
            .then(
                if (isSelected)
                    Modifier.border(
                        figmaDp(2f),
                        Color(0xFF7C5CFF),
                        shape
                    )
                else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {


        if (showNumber && !isLocked) {
            Text(
                text = number,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.4.sp,
                    fontWeight = FontWeight(400),
                    color = if (isDarkColor) Color.White else Color.Black,
                    textAlign = TextAlign.Center
                )
            )
        }


        if (isLocked) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Color(0x80000000),
                        shape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = if (isDarkColor) Color.White else Color(0xFF2E2E2E),
                    modifier = Modifier.size(figmaDp(16f))
                )
            }
        }
    }
}



@Preview
@Composable
fun ColorMapScreenPreview() {
    ColorMapScreen()
}
