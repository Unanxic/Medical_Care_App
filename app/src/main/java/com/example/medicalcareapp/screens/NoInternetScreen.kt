package com.example.medicalcareapp.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.extensions.ContextExtensions
import com.example.medicalcareapp.R
import com.example.medicalcareapp.composables.ButtonComponent
import com.example.medicalcareapp.event_manager.AppEvents
import com.example.medicalcareapp.event_manager.EventManager
import com.example.medicalcareapp.ui.theme.Honeydew
import com.example.medicalcareapp.ui.theme.Olivine
import org.koin.compose.koinInject

@Composable
fun NoInternetScreen(
    eventManager: EventManager = koinInject(),
) {
    val contextExtensions: ContextExtensions = koinInject()

    val onTryAgain = {
        if (contextExtensions.isInternetAvailable()) {
            eventManager.emitEvent(AppEvents.TryAgain)
        }
    }
    BackHandler {
        onTryAgain()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Olivine),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_9),
            contentDescription = "No Internet",
            modifier = Modifier
                .padding(horizontal = 55.dp)
                .padding(top = 94.dp)
        )
        Text(
            text = stringResource(R.string.no_connection),
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.no_internet_connection_found),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Light
        )
        Text(
            text = stringResource(R.string.check_your_connection_and_try_again),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(29.dp))
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Honeydew,
                        shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.waves),
                    contentDescription = "Waves",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ButtonComponent(
                        onClick = { onTryAgain() },
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .height(50.dp)
                            .padding(horizontal = 55.dp)
                            .align(Alignment.CenterHorizontally),
                        text = stringResource(R.string.try_again),
                        isFilled = true,
                        fontSize = 20.sp,
                        cornerRadius = 20,
                        fillColorChoice = Olivine
                    )
                }
            }

        }
    }
}