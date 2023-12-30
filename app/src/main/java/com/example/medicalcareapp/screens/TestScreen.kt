package com.example.medicalcareapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.medicalcareapp.ui.theme.Olivine

@Composable
fun TestScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Olivine)
    ) {
       Text(
           text = "success login"
       )
    }

}