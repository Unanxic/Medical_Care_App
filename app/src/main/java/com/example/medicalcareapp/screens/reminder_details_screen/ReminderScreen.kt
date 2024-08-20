package com.example.medicalcareapp.screens.reminder_details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.domain.models.reminder.Reminder
import com.example.medicalcareapp.composables.ButtonComponent
import com.example.medicalcareapp.composables.GenericButtonSwitch
import com.example.medicalcareapp.extesions.CARD_ELEVATION
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.screens.reminder_screen.viewmodel.ReminderViewModel
import com.example.medicalcareapp.ui.theme.AliceBlue
import com.example.medicalcareapp.ui.theme.EerieBlack
import com.example.medicalcareapp.ui.theme.HookersGreen
import org.koin.compose.koinInject


@Composable
fun ReminderScreen(
    navController: NavController,
    reminderId: String,
    medicationName: String,
    selectedTime: String,
    viewModel: ReminderViewModel = koinInject(),
) {
    LaunchedEffect(reminderId) {
        viewModel.loadReminderById(reminderId, medicationName)
    }

    val reminder by viewModel.currentReminder.collectAsState()
    var isNavigationInProgress by remember { mutableStateOf(false) }
    var isButtonEnabled by remember { mutableStateOf(false) }
    var selectedAction by remember { mutableStateOf<String?>(null) }

    val formattedSelectedTime = selectedTime.toFormattedTimeString()


    Box(
        Modifier
            .fillMaxSize()
            .background(color = HookersGreen),
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
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 45.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = reminder.medicineName,
                color = EerieBlack,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
            )
            Spacer(modifier = Modifier.height(10.dp))
            ColoredCard(reminder, formattedSelectedTime)// Pass the selectedTime
            Spacer(modifier = Modifier.height(60.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                GenericButtonSwitch(
                    text = "Skipped",
                    isSelected = selectedAction == "Skipped",
                    onClick = {
                        selectedAction = "Skipped"
                        isButtonEnabled = true
                    }
                )
                Spacer(modifier = Modifier.width(20.dp))
                GenericButtonSwitch(
                    text = "Taken",
                    isSelected = selectedAction == "Taken",
                    onClick = {
                        selectedAction = "Taken"
                        isButtonEnabled = true
                    }
                )
            }
            Spacer(modifier = Modifier.height(60.dp))
            ButtonComponent(
                modifier = Modifier
                    .height(50.dp)
                    .width(250.dp),
                text = "Done",
                isFilled = true,
                isDisabled = !isButtonEnabled,
                onClick = {
                    if (selectedAction != null) {
                        val isTaken = selectedAction == "Taken"
                        val isSkipped = selectedAction == "Skipped"
                        viewModel.updateReminderStatus(
                            reminderId = reminderId,
                            selectedTime = selectedTime,
                            isTaken = isTaken,
                            isSkipped = isSkipped
                        ) // Pass the selectedTime
                        navController.popBackStack()
                    }
                }
            )
        }
    }
}

@Composable
fun ColoredCard(reminder: Reminder, selectedTime: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        colors = CardDefaults.cardColors(
            containerColor = AliceBlue
        ),
        shape = RoundedCornerShape(40.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = CARD_ELEVATION)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Scheduled Time",
                color = EerieBlack,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Text(
                text = selectedTime,
                color = EerieBlack,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )
        }
    }
}

private fun String.toFormattedTimeString(): String {
    return try {
        val inputFormat = java.text.SimpleDateFormat("HH:mm:ss.SSS", java.util.Locale.getDefault())
        val date = inputFormat.parse(this)
        val outputFormat = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
        date?.let { outputFormat.format(it) } ?: this
    } catch (e: Exception) {
        this
    }
}