package com.example.medicalcareapp.extesions

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


fun Modifier.setNoRippleClickable(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null
    ) {
        onClick()
    }
}

fun String.isEmailValid(): Boolean {
    val emailRegex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}".toRegex()
    return if (this.isEmpty()) {
        false
    } else {
        this.matches(emailRegex)
    }
}

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.coloredShadow(
    color: Color,
    alpha: Float = 0.12f,
    borderRadius: Dp = 0.dp,
    shadowRadius: Dp = 16.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    noTopShadow: Boolean = false,
    onlyTopShadow: Boolean = false,
    onlyBottomShadow: Boolean = false,
    rectangleShapeShadow: Boolean = false,
    circleShadow: Boolean = false,
    circleShadowOffset: Offset = Offset(0f, 0f)
): Modifier = composed {

    val shadowColor = color.copy(alpha = alpha).toArgb()
    val transparent = color.copy(alpha = 0f).toArgb()

    this.drawBehind {

        this.drawIntoCanvas {
            val paint = Paint()
            paint.isAntiAlias = true
            val frameworkPaint = paint.asFrameworkPaint()
            frameworkPaint.color = transparent

            frameworkPaint.setShadowLayer(
                shadowRadius.toPx(),
                offsetX.toPx(),
                offsetY.toPx(),
                shadowColor
            )
            if (onlyTopShadow) { // TODO try and fix this at some point to make it like "onlyBottomShadow"
                it.drawRect(
                    0f,
                    0f,
                    this.size.width,
                    shadowRadius.toPx(),
                    paint
                )
            } else if (onlyBottomShadow){
                it.drawRect(
                    0f,
                    this.size.height,
                    this.size.width,
                    this.size.height + shadowRadius.toPx() / 2,
                    paint
                )
            }else {
                if (rectangleShapeShadow)
                    it.drawRect(
                        0f,
                        if (noTopShadow) shadowRadius.toPx() else 0f,
                        this.size.width,
                        this.size.height,
                        paint
                    )
                else if (circleShadow)
                    it.drawCircle(
                        center = Offset(
                            (this.size.width / 2) + circleShadowOffset.x,
                            (this.size.height / 2) + circleShadowOffset.y
                        ),
                        radius = (this.size.width / 2) + shadowRadius.value,
                        paint
                    )
                else it.drawRoundRect(
                    0f,
                    if (noTopShadow) shadowRadius.toPx() else 0f,
                    this.size.width,
                    this.size.height,
                    borderRadius.toPx(),
                    borderRadius.toPx(),
                    paint
                )
            }
        }
    }
}