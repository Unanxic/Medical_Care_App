package com.example.medicalcareapp.screens

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicalcareapp.R
import com.example.medicalcareapp.navigation.Screens
import com.example.medicalcareapp.ui.theme.DarkJungleGreen
import com.example.medicalcareapp.ui.theme.Olivine
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    var isVisible by remember { mutableStateOf(false) }
    val fadeInTransition = updateTransition(targetState = isVisible, label = "fadeIn")

    LaunchedEffect(Unit) {
        isVisible = true
        delay(2000)
        navController.navigate(Screens.Welcome.route) {
            popUpTo(0)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Olivine),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val imageAlpha by fadeInTransition.animateFloat(
            transitionSpec = {
                tween(durationMillis = 1000)
            }, label = ""
        ) {
            if (it) 1f else 0f
        }
        Image(
            painter = painterResource(id = R.drawable.heart_component),
            contentDescription = null,
            modifier = Modifier
                .alpha(imageAlpha)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.app_name),
            color = DarkJungleGreen,
            fontSize = 30.sp,
            modifier = Modifier
                .alpha(imageAlpha),
            fontWeight = FontWeight.Light
        )
    }
}