package com.fttx.partner.ui.compose.utils

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fttx.partner.ui.compose.theme.CoolGray30

fun Modifier.conditional(
    condition: Boolean,
    modifier: Modifier.() -> Modifier,
): Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}

@SuppressLint("ComposeComposableModifier")
@Composable
fun Modifier.verticalScrollBarV1(
    state: ScrollState,
    color: Color = CoolGray30,
    ratio: Float = 3f,
    width: Dp = 4.dp,
    alwaysVisible: Boolean = false,
    cornerRadius: Dp = 2.dp,
    rightPadding: Dp = 1.dp,
): Modifier {
    val targetAlpha = if (state.isScrollInProgress) 1f else 0f
    val duration = if (state.isScrollInProgress) 150 else 500

    val alpha by animateFloatAsState(
        targetValue = if (alwaysVisible) 1f else targetAlpha,
        animationSpec = tween(durationMillis = duration),
    )

    return drawWithContent {
        drawContent()

        val needDrawScrollbar = state.isScrollInProgress || alpha > 0.0f
        val barHeight = (this.size.height / ratio)
        val barRange = (this.size.height - barHeight) / state.maxValue
        if (needDrawScrollbar) {
            val position = state.value * barRange
            drawRoundRect(
                color = color.copy(alpha = alpha),
                topLeft = Offset(this.size.width - width.toPx() - rightPadding.toPx(), position),
                size = Size(width.toPx(), barHeight),
                cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx()),
            )
        }
    }
}
