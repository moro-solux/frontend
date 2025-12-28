package com.solux.moro.core.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier =
    composed {
        clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            onClick()
        }
    }

private const val FIGMA_BASE_WIDTH_PX = 375f
private const val TARGET_WIDTH_PX = 1080f
private const val TARGET_SCALE = TARGET_WIDTH_PX / FIGMA_BASE_WIDTH_PX // 2.88f

@Composable
fun figmaDp(px375: Float): Dp {
    val density = LocalDensity.current.density
    val targetPx = px375 * TARGET_SCALE
    return (targetPx / density).dp
}

