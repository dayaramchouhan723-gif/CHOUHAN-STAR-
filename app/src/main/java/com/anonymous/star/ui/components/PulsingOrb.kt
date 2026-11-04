package com.anonymous.star.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PulsingOrb(
    modifier: Modifier = Modifier,
    baseSize: Dp = 220.dp,
    sizeScale: Float = 1.0f,
    hueShift: Float = 0f, // 0 to 360
    orbMode: String = "Energy", // Classic, Energy, Neon
    systemNodes: Boolean = true,
    auraBorder: Boolean = true,
    auraSignature: Boolean = true,
    auraCore: Boolean = true,
    hapticFeedback: Boolean = true,
    onClick: () -> Unit = {}
) {
    val haptics = LocalHapticFeedback.current

    val infiniteTransition = rememberInfiniteTransition(label = "OrbPulse")
    
    val pulseAnim by infiniteTransition.animateFloat(
        initialValue = 0.88f,
        targetValue = 1.12f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Pulse"
    )

    val rotationAnim by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Rotation"
    )

    // Dynamic HSV color calculation for smooth hue shifts
    val normalizedHue = (hueShift % 360f + 360f) % 360f
    val baseColor = Color.hsv(hue = normalizedHue, saturation = 0.88f, value = 1.0f)

    val innerGlowColor = baseColor.copy(alpha = 0.85f)
    val outerAuraColor = baseColor.copy(alpha = 0.35f)

    val actualSize = baseSize * sizeScale.coerceIn(0.5f, 2.0f)

    Box(
        modifier = modifier
            .size(actualSize)
            .testTag("pulsing_orb")
            .clickable {
                if (hapticFeedback) {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                }
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerOffset = Offset(size.width / 2f, size.height / 2f)
            val radius = (size.minDimension / 2f) * 0.75f * pulseAnim

            // Outer Aura Glow Ring
            if (auraBorder) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(outerAuraColor, Color.Transparent),
                        center = centerOffset,
                        radius = radius * 1.5f
                    ),
                    center = centerOffset,
                    radius = radius * 1.4f
                )

                // Cyber Ring
                drawCircle(
                    color = baseColor.copy(alpha = 0.5f),
                    center = centerOffset,
                    radius = radius * 1.15f,
                    style = Stroke(width = 3.dp.toPx())
                )
            }

            // Aura Signature Rings
            if (auraSignature) {
                drawCircle(
                    color = baseColor.copy(alpha = 0.25f),
                    center = centerOffset,
                    radius = radius * 1.3f,
                    style = Stroke(width = 1.5.dp.toPx())
                )
            }

            // Core Gradient Sphere
            if (auraCore) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = when (orbMode) {
                            "Neon" -> listOf(Color.White, baseColor, Color.Black)
                            "Classic" -> listOf(baseColor, baseColor.copy(alpha = 0.5f), Color.Transparent)
                            else -> listOf(Color.White, innerGlowColor, baseColor.copy(alpha = 0.3f))
                        },
                        center = centerOffset,
                        radius = radius
                    ),
                    center = centerOffset,
                    radius = radius
                )
            }

            // Orbital System Nodes
            if (systemNodes) {
                val nodeCount = 6
                for (i in 0 until nodeCount) {
                    val angle = Math.toRadians((rotationAnim + i * (360 / nodeCount)).toDouble())
                    val nodeDistance = radius * 1.25f
                    val x = centerOffset.x + (nodeDistance * cos(angle)).toFloat()
                    val y = centerOffset.y + (nodeDistance * sin(angle)).toFloat()

                    drawCircle(
                        color = baseColor,
                        center = Offset(x, y),
                        radius = 4.dp.toPx()
                    )
                }
            }
        }
    }
}
