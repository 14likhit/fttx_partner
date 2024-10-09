package com.fttx.partner.ui.compose.utils

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.dp

fun Modifier.shimmerBackground(shape: Shape = RoundedCornerShape(4.dp)): Modifier =
    composed {
        val transition = rememberInfiniteTransition()

        val translateAnimation by transition.animateFloat(
            initialValue = 0f,
            targetValue = 400f,
            animationSpec =
                infiniteRepeatable(
                    tween(durationMillis = 2000, easing = LinearOutSlowInEasing),
                    RepeatMode.Restart,
                ),
        )

        val shimmerThemeColor = Color.LightGray

        val shimmerColors =
            listOf(
                shimmerThemeColor.copy(alpha = 0.8f),
                shimmerThemeColor.copy(alpha = 0.4f),
            )

        val brush =
            Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(translateAnimation, translateAnimation),
                end = Offset(translateAnimation + 100f, translateAnimation + 100f),
                tileMode = TileMode.Mirror,
            )
        return@composed this.then(background(brush, shape))
    }
