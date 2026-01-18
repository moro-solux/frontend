package com.solux.moro.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.component.top.TopBarBack
import com.solux.moro.core.util.figmaDp

data class ColorItem(
    val colorId: Long,
    val hexCode: String,
    val color: Color,
    val number: String,
    val isLocked: Boolean = false
)

@Composable
fun ColorMapScreen(
    navController: NavHostController,
    viewModel: ColorMapViewModel = hiltViewModel()
) {
    val currentTheme by viewModel.currentTheme.collectAsState()
    val colorList by viewModel.colorList.collectAsState()

    var isNumberOn by remember { mutableStateOf(true) }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner, currentTheme) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadThemeData(currentTheme.name)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val currentGrid = remember(colorList) {
        colorList.map { dto ->
            ColorItem(
                colorId = dto.colorId,
                hexCode = dto.hexCode,
                color = try {
                    val hexString = if (dto.hexCode.startsWith("#")) dto.hexCode else "#${dto.hexCode}"
                    Color(android.graphics.Color.parseColor(hexString))
                } catch (e: Exception) {
                    Color.Gray
                },
                number = dto.postCount.toString(),
                isLocked = !dto.unlocked
            )
        }.chunked(6)
    }

    Scaffold(
        topBar = { TopBarBack(
            title = "컬러맵",
            onBackClick = {
                navController.popBackStack()
            }
        ) },
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
                    onThemeChange = { theme ->
                        viewModel.updateTheme(theme)
                    }
                )

                ColorMapSection(
                    colorGrid = currentGrid,
                    showNumber = isNumberOn,
                    onColorClick = { item ->
                        val cleanHex = item.hexCode.replace("#", "")
                        navController.navigate("selected_color/${item.colorId}/$cleanHex")
                    }
                )
            }
        }
    }
}


@Composable
fun ColorMapSection(
    colorGrid: List<List<ColorItem>>,
    showNumber: Boolean,
    onColorClick: (ColorItem) -> Unit
) {
    Column(
        modifier = Modifier
            .width(figmaDp(375f))
            .padding(horizontal = figmaDp(16f), vertical = figmaDp(20f)),
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
                        number = item.number,
                        onClick = { onColorClick(item) }
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
    number: String = "12",
    onClick: () -> Unit = {}
) {
    val isDarkColor = color.luminance() < 0.15f
    val shape = RoundedCornerShape(figmaDp(9.47514f))

    Box(
        modifier = Modifier
            .size(figmaDp(48.83333f))
            .clip(shape)
            .background(color)
            .clickable(enabled = !isLocked) { onClick() }
            .then(
                if (isDarkColor) Modifier.border(figmaDp(1f), Color(0xFF404040), shape)
                else Modifier
            )
            .then(
                if (isSelected) Modifier.border(figmaDp(2f), Color(0xFF7C5CFF), shape)
                else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        if (showNumber && !isLocked) {
            Text(
                text = number,
                style = TextStyle(
                    fontSize = 16.sp,
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
                    .background(Color(0x80000000), shape),
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
) {
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
            Row(
                modifier = Modifier
                    .width(figmaDp(168f))
                    .height(figmaDp(48f)),
                horizontalArrangement = Arrangement.spacedBy(figmaDp(12f), Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // VIVID
                Box(
                    modifier = Modifier
                        .size(figmaDp(48f))
                        .clip(CircleShape)
                        .clickable { onThemeChange(ColorThemeType.VIVID) }
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFFDE3323), Color(0xFFE26F2E), Color(0xFFFCFE57), Color(0xFF6AC83E)),
                                start = Offset.Zero, end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                            )
                        )
                        .border(width = figmaDp(if (currentTheme == ColorThemeType.VIVID) 2f else 0f), color = Color(0xFFF2F2F2), shape = CircleShape)
                )
                // PASTEL
                Box(
                    modifier = Modifier
                        .size(figmaDp(48f))
                        .clip(CircleShape)
                        .clickable { onThemeChange(ColorThemeType.PASTEL) }
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFFFF6CB4), Color(0xFFFFBC4F), Color(0xFFDEFF9C), Color(0xFFCAFFC6)),
                                start = Offset.Zero, end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                            )
                        )
                        .border(width = figmaDp(if (currentTheme == ColorThemeType.PASTEL) 2f else 0f), color = Color(0xFFF2F2F2), shape = CircleShape)
                )
                // NATURE
                Box(
                    modifier = Modifier
                        .size(figmaDp(48f))
                        .clip(CircleShape)
                        .clickable { onThemeChange(ColorThemeType.NATURE) }
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFF8EDBFE), Color(0xFF9EE2DB), Color(0xFFF0AA58)),
                                start = Offset.Zero, end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                            )
                        )
                        .border(width = figmaDp(if (currentTheme == ColorThemeType.NATURE) 2f else 0f), color = Color(0xFFF2F2F2), shape = CircleShape)
                )
            }

            Num_Button(
                isOn = isOn,
                onToggle = onToggle
            )
        }
    }
}

enum class ColorThemeType {
    PASTEL,
    VIVID,
    NATURE
}