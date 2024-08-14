package com.example.peoplelisting.ui.listpeople.view

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.peoplelisting.ui.theme.AppTheme

@Composable
fun ShimmeringView(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .border(3.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
            .background(
                shimmeringBrush(
                    backGround = MaterialTheme.colorScheme.secondary, LocalContext.current
                        .resources.displayMetrics.widthPixels.toFloat()
                ),
                RoundedCornerShape(16.dp)
            )
    )

}

@Composable
fun ShimmeringList(modifier: Modifier = Modifier) {
        Column(
            modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp), verticalArrangement =
            Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (i in 0..10) {
                ShimmeringView(modifier = Modifier)
            }
        }
}

@Composable
fun shimmeringBrush(backGround: Color, targetValue: Float = 1500f): Brush {
    val colors = listOf(
        backGround.copy(),
        backGround.copy(0.3f),
        backGround.copy()
    )
    val transition = rememberInfiniteTransition(label = "")
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing), repeatMode = RepeatMode.Restart
        ), label = ""
    )
    return Brush.linearGradient(
        colors = colors,
        start = Offset.Zero,
        end = Offset(translateAnimation.value, translateAnimation.value)
    )

}