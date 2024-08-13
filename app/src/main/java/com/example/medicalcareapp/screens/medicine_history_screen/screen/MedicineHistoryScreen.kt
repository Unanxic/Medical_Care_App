package com.example.medicalcareapp.screens.medicine_history_screen.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicalcareapp.R
import com.example.medicalcareapp.composables.ButtonComponent
import com.example.medicalcareapp.composables.GenericClickableRowWithIcons
import com.example.medicalcareapp.composables.IconType
import com.example.medicalcareapp.event_manager.EventManager
import com.example.medicalcareapp.extesions.bouncingClickable
import com.example.medicalcareapp.extesions.medicineNavigateSingleTop
import com.example.medicalcareapp.navigation.Screens
import com.example.medicalcareapp.screens.medicine_history_screen.viewmodels.MedicationViewModel
import com.example.medicalcareapp.ui.theme.HookersGreen
import com.example.medicalcareapp.ui.theme.MSUGreen
import org.koin.compose.koinInject

@Composable
fun MedicineHistoryScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    eventManager: EventManager = koinInject(),
    viewModel: MedicationViewModel = koinInject(),
) {

    val medications by viewModel.medications.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
            .background(color = HookersGreen)
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading -> {
                // Show a loading spinner while data is being fetched
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(60.dp)
                )
            }
            medications.isEmpty() -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.looking_icon),
                        contentDescription = null,
                        modifier = Modifier.size(200.dp)
                    )
                    Text(
                        text = stringResource(R.string.no_medicine_history),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(70.dp))
                    ButtonComponent(
                        onClick = {
                            navController.medicineNavigateSingleTop(Screens.AddMedicine.route)
                        },
                        modifier = Modifier
                            .height(50.dp)
                            .width(215.dp),
                        text = stringResource(R.string.add_medicine),
                        isFilled = true,
                        fontSize = 16.sp,
                        cornerRadius = 20,
                        fillColorChoice = MSUGreen,
                        contentColorChoice = Color.White
                    )
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 15.dp)
                ) {
                    medications.forEach { medication ->
                        val iconType = try {
                            IconType.valueOf(medication.formOfMedicine.uppercase())
                        } catch (e: IllegalArgumentException) {
                            IconType.OTHER // Default to a known icon type
                        }

                        GenericClickableRowWithIcons(
                            icon = iconType,
                            text = medication.medication,
                            onClick = { navController.medicineNavigateSingleTop("${Screens.MedicineDetails.route}/${medication.id}") }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                AddMoreButton(
                    onClick = {
                        navController.medicineNavigateSingleTop(Screens.AddMedicine.route)
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 13.dp)
                )
            }
        }
    }
}

@Composable
fun AddMoreButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.add_circle),
        contentDescription = null,
        modifier = modifier.bouncingClickable {
            onClick()
        }
    )
}