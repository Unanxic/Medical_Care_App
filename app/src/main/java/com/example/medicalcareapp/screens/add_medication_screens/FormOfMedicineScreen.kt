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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicalcareapp.R
import com.example.medicalcareapp.composables.ButtonComponent
import com.example.medicalcareapp.extesions.medicineNavigateSingleTop
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.navigation.Screens
import com.example.medicalcareapp.ui.theme.AliceBlue
import com.example.medicalcareapp.ui.theme.EerieBlack
import com.example.medicalcareapp.ui.theme.LightBlue
import com.example.medicalcareapp.ui.theme.PewterBlue
import com.example.medicalcareapp.ui.theme.SmokyBlack

@Composable
fun FormOfMedicineScreen(
    navController: NavController
) {
    var isNavigationInProgress by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PewterBlue),
    ) {
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .size(25.dp)
                .setNoRippleClickable {
                    if (!isNavigationInProgress) {
                        isNavigationInProgress = true
                        navController.popBackStack()
                    }
                },
            tint = EerieBlack
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 87.dp, bottom = 38.dp)
                .padding(horizontal = 35.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Image(
                painter = painterResource(id = R.drawable.injection),
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.what_form_is_the_medicine),
                color = EerieBlack,
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
                        color = AliceBlue,
                        shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .imePadding()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 32.dp, vertical = 66.dp)
                ) {
                    //Pill
                    ButtonComponent(
                        onClick = {
                            if (!isNavigationInProgress) {
                                isNavigationInProgress = true
                                navController.medicineNavigateSingleTop(Screens.Condition.route)
                            }
                        },
                        modifier = Modifier
                            .height(50.dp)
                            .width(250.dp)
                            .align(Alignment.CenterHorizontally),
                        text = stringResource(R.string.pill),
                        isFilled = true,
                        fontSize = 20.sp,
                        cornerRadius = 40,
                        fillColorChoice = LightBlue,
                        contentColorChoice = SmokyBlack,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    //Solution
                    ButtonComponent(
                        onClick = { /* todo */ },
                        modifier = Modifier
                            .height(50.dp)
                            .width(250.dp)
                            .align(Alignment.CenterHorizontally),
                        text = stringResource(R.string.solution),
                        isFilled = true,
                        fontSize = 20.sp,
                        cornerRadius = 40,
                        fillColorChoice = LightBlue,
                        contentColorChoice = SmokyBlack
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    //Inhaler
                    ButtonComponent(
                        onClick = { /* todo */ },
                        modifier = Modifier
                            .height(50.dp)
                            .width(250.dp)
                            .align(Alignment.CenterHorizontally),
                        text = stringResource(R.string.inhaler),
                        isFilled = true,
                        fontSize = 20.sp,
                        cornerRadius = 40,
                        fillColorChoice = LightBlue,
                        contentColorChoice = SmokyBlack
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    //Drops
                    ButtonComponent(
                        onClick = { /* todo */ },
                        modifier = Modifier
                            .height(50.dp)
                            .width(250.dp)
                            .align(Alignment.CenterHorizontally),
                        text = stringResource(R.string.drops),
                        isFilled = true,
                        fontSize = 20.sp,
                        cornerRadius = 40,
                        fillColorChoice = LightBlue,
                        contentColorChoice = SmokyBlack
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    //Injection
                    ButtonComponent(
                        onClick = { /* todo */ },
                        modifier = Modifier
                            .height(50.dp)
                            .width(250.dp)
                            .align(Alignment.CenterHorizontally),
                        text = stringResource(R.string.injection),
                        isFilled = true,
                        fontSize = 20.sp,
                        cornerRadius = 40,
                        fillColorChoice = LightBlue,
                        contentColorChoice = SmokyBlack
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    //Other
                    ButtonComponent(
                        onClick = { /* todo */ },
                        modifier = Modifier
                            .height(50.dp)
                            .width(250.dp)
                            .align(Alignment.CenterHorizontally),
                        text = stringResource(R.string.other),
                        isFilled = true,
                        fontSize = 20.sp,
                        cornerRadius = 40,
                        fillColorChoice = LightBlue,
                        contentColorChoice = SmokyBlack
                    )
                }
            }
        }
    }
}