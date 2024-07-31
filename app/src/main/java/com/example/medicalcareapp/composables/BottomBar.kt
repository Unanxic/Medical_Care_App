package com.example.medicalcareapp.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalcareapp.R
import com.example.medicalcareapp.extesions.coloredShadow
import com.example.medicalcareapp.managers.CurrentHomeScreen
import com.example.medicalcareapp.managers.HomeScreenManager
import com.example.medicalcareapp.ui.theme.EerieBlack
import com.example.medicalcareapp.ui.theme.HookersGreen
import com.example.medicalcareapp.ui.theme.PastelBlue
import kotlinx.coroutines.Dispatchers
import org.koin.compose.koinInject

@Composable
fun BottomBar(
    homeScreenManager: HomeScreenManager = koinInject()
) {

    val currentScreen by homeScreenManager.currentHomeScreen.collectAsState(CurrentHomeScreen.NONE)

    Surface(
        modifier = Modifier.coloredShadow(
            color = Color.Black,
            shadowRadius = 10.dp,
            onlyTopShadow = true,
            alpha = 0.3f,
            rectangleShapeShadow = true
        ),
        color = PastelBlue
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ImageComponent(
                    painter = painterResource(id = R.drawable.chart_line),
                    contentDescription = "medication icon",
                    labelResId = stringResource(R.string.calendar),
                    onClick = {
                        homeScreenManager.emitCurrentScreen(CurrentHomeScreen.CALENDAR)
                    },
                    colorFilter = if (currentScreen == CurrentHomeScreen.CALENDAR) ColorFilter.tint(
                        HookersGreen
                    ) else null
                )
                ImageComponent(
                    painter = painterResource(id = R.drawable.medicines),
                    contentDescription = "medication icon",
                    labelResId = stringResource(R.string.medicine),
                    onClick = {
                        homeScreenManager.emitCurrentScreen(CurrentHomeScreen.HISTORY)
                    },
                    colorFilter = if (currentScreen == CurrentHomeScreen.HISTORY) ColorFilter.tint(
                        HookersGreen
                    ) else null
                )
                ImageComponent(
                    painter = painterResource(id = R.drawable.accident_and_emergency),
                    contentDescription = "emergency contacts icon",
                    labelResId = stringResource(R.string.contacts),
                    onClick = {
                        homeScreenManager.emitCurrentScreen(CurrentHomeScreen.CONTACTS)
                    },
                    colorFilter = if (currentScreen == CurrentHomeScreen.CONTACTS) ColorFilter.tint(
                        HookersGreen
                    ) else null
                )
                ImageComponent(
                    painter = painterResource(id = R.drawable.clinical),
                    contentDescription = "account icon",
                    labelResId = stringResource(R.string.more),
                    onClick = {
                        homeScreenManager.emitCurrentScreen(CurrentHomeScreen.ACCOUNT)
                    },
                    colorFilter = if (currentScreen == CurrentHomeScreen.ACCOUNT) ColorFilter.tint(
                        HookersGreen
                    ) else null
                )
            }
        }
    }
}

@Composable
fun ImageComponent(
    modifier: Modifier = Modifier,
    painter: Painter,
    labelResId: String = "",
    contentDescription: String?,
    onClick: () -> Unit,
    colorFilter: ColorFilter? = null,
) {

    val interactionSource = remember { MutableInteractionSource() }

    var isPressed by remember { mutableStateOf(false) }
    val animateFloat by animateFloatAsState(targetValue = if (isPressed) 0.9f else 1f, label = " ")


    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> isPressed = true
                is PressInteraction.Release,
                is PressInteraction.Cancel,
                -> isPressed = false
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .height(65.dp)
            .padding(vertical = 6.dp)
    ) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            colorFilter = colorFilter,
            modifier = modifier
                .graphicsLayer {
                    val scale = animateFloat
                    scaleX = scale
                    scaleY = scale
                }
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    onClick()
                }
        )
        Text(
            text = labelResId.uppercase(),
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
            color = EerieBlack,
            maxLines = 1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
private fun BottomBarPreview() {
    val homeScreenManager = HomeScreenManager(Dispatchers.IO)
    BottomBar(homeScreenManager = homeScreenManager)
}


