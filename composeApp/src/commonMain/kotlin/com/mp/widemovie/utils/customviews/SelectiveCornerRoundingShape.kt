package com.mp.widemovie.utils.customviews

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

class SelectiveCornerRoundingShape(
    private val cornerRadius: Dp,
    private val clipTopStart: Boolean = false,
    private val clipTopEnd: Boolean = false,
    private val clipBottomStart: Boolean = false,
    private val clipBottomEnd: Boolean = false
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val radiusPx = with(density) { cornerRadius.toPx() }

        val path = Path().apply {
            val rect = Rect(Offset.Zero, size)

            // Determine the corner radii based on selection and layout direction
            val resolvedClipTopStart = if (layoutDirection == LayoutDirection.Ltr) clipTopStart else clipTopEnd
            val resolvedClipTopEnd = if (layoutDirection == LayoutDirection.Ltr) clipTopEnd else clipTopStart
            val resolvedClipBottomStart = if (layoutDirection == LayoutDirection.Ltr) clipBottomStart else clipBottomEnd
            val resolvedClipBottomEnd = if (layoutDirection == LayoutDirection.Ltr) clipBottomEnd else clipBottomStart

            val topStartRadius = if (resolvedClipTopStart) radiusPx else 0f
            val topEndRadius = if (resolvedClipTopEnd) radiusPx else 0f
            val bottomEndRadius = if (resolvedClipBottomEnd) radiusPx else 0f
            val bottomStartRadius = if (resolvedClipBottomStart) radiusPx else 0f

            addRoundRect(
                RoundRect(
                    rect = rect,
                    topLeft = CornerRadius(topStartRadius),
                    topRight = CornerRadius(topEndRadius),
                    bottomRight = CornerRadius(bottomEndRadius),
                    bottomLeft = CornerRadius(bottomStartRadius)
                )
            )
        }
        return Outline.Generic(path)
    }

    // Optional: For better preview support and simple cases, you might provide a simpler equals/hashCode
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SelectiveCornerRoundingShape) return false
        return cornerRadius == other.cornerRadius &&
                clipTopStart == other.clipTopStart &&
                clipTopEnd == other.clipTopEnd &&
                clipBottomStart == other.clipBottomStart &&
                clipBottomEnd == other.clipBottomEnd
    }

    override fun hashCode(): Int {
        var result = cornerRadius.hashCode()
        result = 31 * result + clipTopStart.hashCode()
        result = 31 * result + clipTopEnd.hashCode()
        result = 31 * result + clipBottomStart.hashCode()
        result = 31 * result + clipBottomEnd.hashCode()
        return result
    }
}