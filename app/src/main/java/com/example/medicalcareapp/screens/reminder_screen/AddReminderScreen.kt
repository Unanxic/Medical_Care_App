package com.example.medicalcareapp.screens.reminder_screen

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicalcareapp.composables.DateTextField
import com.example.medicalcareapp.composables.DropDownField
import com.example.medicalcareapp.composables.TimerTextField
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.ui.theme.BlackOlive
import com.example.medicalcareapp.ui.theme.HookersGreen
import com.example.medicalcareapp.ui.theme.SmokyBlack
import com.example.medicalcareapp.utilities.Recurrence
import java.time.LocalTime
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddReminderScreen(
    navController: NavController,
) {
    var isNavigationInProgress by remember { mutableStateOf(false) }

    var recurrence by rememberSaveable { mutableStateOf(Recurrence.Daily.name) }
    var endDate by rememberSaveable { mutableLongStateOf(Date().time) }

    var selectedTime by rememberSaveable { mutableStateOf(LocalTime.now()) }

    Column(
        Modifier
            .fillMaxSize()
            .background(color = HookersGreen),
    ) {
        TopBarDetails(
            onBackClick = {
                if (!isNavigationInProgress) {
                    isNavigationInProgress = true
                    navController.popBackStack()
                }
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "Recurrence",
                color = SmokyBlack,
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(6.dp))
            DropDownField(
                options = listOf(Recurrence.Daily.name, Recurrence.Weekly.name, Recurrence.Monthly.name)
            ) { selectedRecurrence ->
                recurrence = selectedRecurrence
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Start Date",
                color = SmokyBlack,
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(6.dp))
            DateTextField(
                initialDate = endDate,
                onDateSelected = { endDate = it }
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "End Date",
                color = SmokyBlack,
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(6.dp))
            DateTextField(
                initialDate = endDate,
                onDateSelected = { endDate = it }
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Time(s) for Medication",
                color = SmokyBlack,
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(6.dp))
            TimerTextField(
                initialTime = selectedTime,
                onTimeSelected = { selectedTime = it }
            )
        }
    }
}

@Composable
private fun TopBarDetails(
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(HookersGreen)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 16.dp)
                .size(25.dp)
                .setNoRippleClickable {
                    onBackClick()
                },
            tint = SmokyBlack
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add Reminder",
            color = BlackOlive,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}