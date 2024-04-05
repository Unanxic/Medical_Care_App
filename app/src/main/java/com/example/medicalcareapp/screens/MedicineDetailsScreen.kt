package com.example.medicalcareapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalcareapp.R
import com.example.medicalcareapp.composables.ButtonComponent
import com.example.medicalcareapp.extesions.CARD_ELEVATION
import com.example.medicalcareapp.ui.theme.DarkJungleGreen
import com.example.medicalcareapp.ui.theme.Honeydew
import com.example.medicalcareapp.ui.theme.Olivine
import com.example.medicalcareapp.ui.theme.SmokyBlack
import com.example.medicalcareapp.ui.theme.TeaGreen


@Composable
fun MedicineDetailsScreen(
    paddingValues: PaddingValues,
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(color = Olivine)
            .padding(top = paddingValues.calculateTopPadding()),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            ColoredCard()
            Spacer(modifier = Modifier.height(60.dp))
            ButtonComponent(
                onClick = {
                    //todo
                },
                modifier = Modifier
                    .height(50.dp)
                    .width(150.dp),
                text = stringResource(R.string.delete),
                isFilled = true,
                fontSize = 20.sp,
                cornerRadius = 20,
                fillColorChoice = Honeydew,
                contentColorChoice = SmokyBlack
            )
        }
        Image(
            painter = painterResource(id = R.drawable.waves),
            contentDescription = "Waves",
            contentScale = ContentScale.FillWidth,
            colorFilter = ColorFilter.tint(Honeydew),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun ColoredCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(314.dp),
        colors = CardDefaults.cardColors(
            containerColor = TeaGreen
        ),
        shape = RoundedCornerShape(40.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = CARD_ELEVATION)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.condition),
                color = DarkJungleGreen,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Text(
                text = "asthma",
                color = DarkJungleGreen,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(34.dp))
            Text(
                text = stringResource(R.string.type),
                color = DarkJungleGreen,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Text(
                text = "inhaler",
                color = DarkJungleGreen,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(34.dp))
            Text(
                text = stringResource(R.string.allergy),
                color = DarkJungleGreen,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.yes_no),
                color = DarkJungleGreen,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun PreviewMedicineDetailsScreen() {
    BoxWithConstraints {
        MedicineDetailsScreen(paddingValues = PaddingValues(all = 16.dp))
    }
}