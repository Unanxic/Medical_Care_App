package com.example.medicalcareapp.screens.add_medication_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalcareapp.R
import com.example.medicalcareapp.composables.ButtonComponent
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.ui.theme.DarkJungleGreen
import com.example.medicalcareapp.ui.theme.Honeydew
import com.example.medicalcareapp.ui.theme.SmokyBlack
import com.example.medicalcareapp.ui.theme.TeaGreen

@Composable
fun AddAllergyScreen() {
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
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .size(25.dp)
                .setNoRippleClickable {
                    //todo
                },
            tint = DarkJungleGreen
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 87.dp, bottom = 38.dp)
                .padding(horizontal = 35.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Image(
                painter = painterResource(id = R.drawable.blue_circle),
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.are_you_allergic_to_this_medicine),
                color = DarkJungleGreen,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 250.dp)
                    .background(
                        color = Honeydew,
                        shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .imePadding()
                        .verticalScroll(rememberScrollState())
                        .padding(top = 66.dp)
                        .padding(horizontal = 32.dp)
                ) {
                    //Yes
                    ButtonComponent(
                        onClick = { /* todo */ },
                        modifier = Modifier
                            .height(50.dp)
                            .width(250.dp)
                            .align(Alignment.CenterHorizontally),
                        text = stringResource(R.string.yes),
                        isFilled = true,
                        fontSize = 20.sp,
                        cornerRadius = 40,
                        fillColorChoice = TeaGreen,
                        contentColorChoice = SmokyBlack,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    //No
                    ButtonComponent(
                        onClick = { /* todo */ },
                        modifier = Modifier
                            .height(50.dp)
                            .width(250.dp)
                            .align(Alignment.CenterHorizontally),
                        text = stringResource(R.string.no),
                        isFilled = true,
                        fontSize = 20.sp,
                        cornerRadius = 40,
                        fillColorChoice = TeaGreen,
                        contentColorChoice = SmokyBlack
                    )
                }
            }
        }
    }
}