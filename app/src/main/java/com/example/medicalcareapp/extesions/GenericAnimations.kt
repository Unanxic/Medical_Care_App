package com.example.medicalcareapp.extesions

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween

val ANIMATION_DURATION = 400
val DURATION_EASING = FastOutSlowInEasing
fun <T> getDefaultTweenAnimation() = tween<T>(ANIMATION_DURATION, easing = DURATION_EASING)