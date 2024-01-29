package com.example.medicalcareapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.medicalcareapp.R
import com.example.medicalcareapp.event_manager.EventManager
import com.example.medicalcareapp.ui.theme.Olivine
import org.koin.compose.koinInject

@Composable
fun AccountScreen(
    paddingValues: PaddingValues,
    eventManager: EventManager = koinInject(),
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(color = Olivine)
            .padding(top = paddingValues.calculateTopPadding()),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.looking_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
            )
        }
    }
}