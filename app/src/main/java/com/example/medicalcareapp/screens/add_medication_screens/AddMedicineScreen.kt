package com.example.medicalcareapp.screens.add_medication_screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicalcareapp.R
import com.example.medicalcareapp.composables.ButtonComponent
import com.example.medicalcareapp.composables.GenericTextField
import com.example.medicalcareapp.extesions.medicineNavigateSingleTop
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.navigation.Screens
import com.example.medicalcareapp.ui.theme.AliceBlue
import com.example.medicalcareapp.ui.theme.EerieBlack
import com.example.medicalcareapp.ui.theme.MSUGreen
import com.example.medicalcareapp.ui.theme.PewterBlue

@Composable
fun AddMedicineScreen(
    navController: NavController
) {
    var isNavigationInProgress by remember { mutableStateOf(false) }

    var medicineName by rememberSaveable { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PewterBlue),
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
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
                painter = painterResource(id = R.drawable.medication_add),
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.what_medicine_would_you_like_to_add),
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
                        .fillMaxSize()
                        .align(Alignment.TopCenter)
                        .imePadding()
                        .verticalScroll(rememberScrollState())
                        .padding(top = 66.dp)
                        .padding(horizontal = 32.dp),
                ) {
                    GenericTextField(
                        value = medicineName,
                        updateText = {
                            medicineName = it
                        },
                        fontSize = 18.sp,
                        imeAction = ImeAction.Done
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                    ) {
                        AnimatedVisibility(
                            visible = medicineName.isEmpty(),
                            enter = slideInHorizontally(
                                initialOffsetX = { -it },
                                animationSpec = tween(400)
                            ) + fadeIn(animationSpec = tween(400)),
                            exit = slideOutHorizontally(
                                targetOffsetX = { -it },
                                animationSpec = tween(400)
                            ) + fadeOut(animationSpec = tween(400))
                        ) {
                            Text(
                                text = stringResource(R.string.start_typing_your_medicine),
                                color = EerieBlack,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Light
                            )
                        }
                    }
                    Spacer(Modifier.weight(1f))
                    Spacer(Modifier.height(50.dp))
                    ButtonComponent(
                        onClick = {
                            navController.medicineNavigateSingleTop(Screens.FormOfMedicine.route)
                        },
                        modifier = Modifier
                            .height(50.dp)
                            .width(250.dp)
                            .align(Alignment.CenterHorizontally),
                        text = stringResource(R.string.next),
                        isFilled = true,
                        isDisabled = medicineName.isBlank(),
                        fontSize = 20.sp,
                        cornerRadius = 20,
                        fillColorChoice = MSUGreen
                    )
                    Spacer(Modifier.height(70.dp))
                }
            }
        }
    }
}