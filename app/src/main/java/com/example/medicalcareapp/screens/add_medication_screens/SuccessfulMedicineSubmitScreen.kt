package com.example.medicalcareapp.screens.add_medication_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalcareapp.R
import com.example.medicalcareapp.composables.ButtonComponent
import com.example.medicalcareapp.ui.theme.DarkJungleGreen
import com.example.medicalcareapp.ui.theme.SmokyBlack
import com.example.medicalcareapp.ui.theme.TeaGreen

@Composable
fun SuccessfulMedicineSubmitScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_rainbow),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.success),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
            )
            Text(
                text = "Medicine submitted successfully!",
                color = DarkJungleGreen,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(50.dp))
            ButtonComponent(
                onClick = { /* todo */ },
                modifier = Modifier
                    .height(76.dp)
                    .width(307.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Go back to medicine history",
                isFilled = true,
                fontSize = 20.sp,
                cornerRadius = 20,
                fillColorChoice = TeaGreen,
                contentColorChoice = SmokyBlack,

            )
        }
    }
}