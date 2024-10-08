package com.example.medicalcareapp.extesions

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.MotionEvent
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medicalcareapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale


fun Modifier.setNoRippleClickable(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null
    ) {
        onClick()
    }
}

fun NavController.medicineNavigateSingleTop(route: String) = this.navigate(route) {
    launchSingleTop = true
}

fun NavController.medicineNavigateSingleTopWithSecondParameter(route: String, isContactDeleted: Boolean = false) {
    this.navigate(route) {
        popUpTo(route) { inclusive = true }
        launchSingleTop = true
    }
    this.currentBackStackEntry?.arguments?.putBoolean("isContactDeleted", isContactDeleted)
}

fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it ->
    it.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.onClickWithScaleAnimation(scaleFactor: Float = 0.9f, onClick: () -> Unit): Modifier =
    composed {
        var selected by remember { mutableStateOf(false) }
        val scale by animateFloatAsState(
            targetValue = if (selected) scaleFactor else 1f,
            label = ""
        )
        this
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        selected = true
                    }

                    MotionEvent.ACTION_UP -> {
                        selected = false
                        onClick()
                    }

                    else -> {
                        selected = false
                    }
                }
                true
            }
            .then(this.scale(scale))
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

fun Context.makePhoneCall(phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$phoneNumber")
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    this.startActivity(intent)
}

fun Context.sendEmail(emailAddress: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$emailAddress")
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    this.startActivity(intent)
}

fun <T> MutableStateFlow<T>.medicationEmit(scope: CoroutineScope? = null, calculation: () -> T): Job {
    return scope?.let { coroutineScope ->
        coroutineScope.launch {
            this@medicationEmit.emit(calculation())
        }
    } ?: CoroutineScope(Dispatchers.IO).launch {
        this@medicationEmit.emit(calculation())
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.bouncingClickable(
    enabled: Boolean = true,
    pressScaleFactor: Float = 0.97f,
    pressAlphaFactor: Float = 0.7f,
    onLongClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
    onClick: () -> Unit,
) = composed {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val animationTransition = updateTransition(isPressed, label = "BouncingClickableTransition")
    val scaleFactor by animationTransition.animateFloat(
        targetValueByState = { pressed -> if (pressed) pressScaleFactor else 1f },
        label = "BouncingClickableScaleFactorTransition",
    )
    val opacity by animationTransition.animateFloat(
        targetValueByState = { pressed -> if (pressed) pressAlphaFactor else 1f },
        label = "BouncingClickableAlphaTransition",
    )

    this
        .graphicsLayer {
            scaleX = scaleFactor
            scaleY = scaleFactor
            alpha = opacity
        }
        .combinedClickable(
            interactionSource = interactionSource,
            indication = null,
            enabled = enabled,
            onClick = onClick,
            onLongClick = onLongClick,
            onDoubleClick = onDoubleClick,
        )
}

fun Long.toFormattedDateString(): String {
    val sdf = SimpleDateFormat("LLLL dd, yyyy", Locale.getDefault())
    return sdf.format(this)
}



fun getIconType(formOfMedicine: String): IconType {
    return when (formOfMedicine) {
        "Pill", "Χάπι" -> IconType.PILL
        "Solution", "Διάλυμα" -> IconType.SOLUTION
        "Inhaler", "Εισπνευστήρας" -> IconType.INHALER
        "Drops", "Σταγόνες" -> IconType.DROPS
        "Injection", "Ένεση" -> IconType.INJECTION
        else -> IconType.OTHER
    }
}

enum class IconType(@DrawableRes val medicationIcon: Int) {
    INHALER(medicationIcon = R.drawable.inhaler),
    PILL(medicationIcon = R.drawable.pill),
    SOLUTION(medicationIcon = R.drawable.solution),
    DROPS(medicationIcon = R.drawable.drops),
    INJECTION(medicationIcon = R.drawable.injection_icon),
    OTHER(medicationIcon = R.drawable.other)
}
fun getContactIconType(contactType: String): ContactIconType {
    return when (contactType) {
        "Doctor", "Γιατρός" -> ContactIconType.DOCTOR
        "Pharmacy", "Φαρμακείο" -> ContactIconType.PHARMACY
        else -> ContactIconType.CAREGIVER
    }
}
enum class ContactIconType(@DrawableRes val contactIcon: Int, val contactType: String) {
    PHARMACY(contactIcon = R.drawable.pharmacy_icon, contactType = "Pharmacy"),
    DOCTOR(contactIcon = R.drawable.doctor_icon, contactType = "Doctor"),
    CAREGIVER(contactIcon = R.drawable.person_icon, contactType = "Caregiver")
}

@Composable
fun ContactIconType.getLocalizedName(): String {
    return when (this) {
        ContactIconType.DOCTOR -> stringResource(id = R.string.doctor)
        ContactIconType.PHARMACY -> stringResource(id = R.string.pharmacy)
        ContactIconType.CAREGIVER -> stringResource(id = R.string.caregiver)
    }
}

@Composable
fun IconType.getLocalizedName(): String {
    return when (this) {
        IconType.INHALER -> stringResource(id = R.string.inhaler)
        IconType.PILL -> stringResource(id = R.string.pill)
        IconType.SOLUTION -> stringResource(id = R.string.solution)
        IconType.DROPS -> stringResource(id = R.string.drops)
        IconType.INJECTION -> stringResource(id = R.string.injection)
        IconType.OTHER -> stringResource(id = R.string.other)
    }
}
