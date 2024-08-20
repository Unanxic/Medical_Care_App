package com.example.medicalcareapp.screens.calendar_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.domain.models.reminder.Reminder
import com.example.medicalcareapp.R
import com.example.medicalcareapp.composables.GenericClickableRowWithoutIcons
import com.example.medicalcareapp.screens.calendar_screen.composables.DatesHeader
import com.example.medicalcareapp.screens.reminder_screen.viewmodel.ReminderViewModel
import com.example.medicalcareapp.ui.theme.AliceBlue
import com.example.medicalcareapp.ui.theme.HookersGreen
import com.example.medicalcareapp.ui.theme.MSUGreen
import org.koin.compose.koinInject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun CalendarScreen(
    navController: NavController,
    viewModel: ReminderViewModel = koinInject(),
    paddingValues: PaddingValues,
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val todayInMillis = remember { Calendar.getInstance().timeInMillis }
    val selectedDate = remember { mutableLongStateOf(todayInMillis) }

    Box(
        Modifier
            .fillMaxSize()
            .background(color = HookersGreen)
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DatesHeader(onDateSelected = { dateModel ->
                selectedDate.longValue = dateModel.date.time // Update selected date when a new date is selected
            })

            Spacer(modifier = Modifier.height(30.dp))

            when {
                isLoading -> {
                    CircularProgressIndicator()
                }
                else -> {
                    val filteredReminders = viewModel.getRemindersForDate(selectedDate.longValue)
                    if (filteredReminders.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            MedicationBreakCard() // Show this card if there are no reminders for the selected date
                        }
                    } else {
                        ReminderList(reminders = filteredReminders) // Show reminders if there are any
                    }
                }
            }
        }
    }
}

@Composable
fun ReminderList(reminders: List<Reminder>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        reminders.forEach { reminder ->
            val times = listOfNotNull(reminder.timeOne, reminder.timeTwo, reminder.timeThree, reminder.timeFour)
            times.forEach { time ->
                val formattedTime = formatTime(time)
                GenericClickableRowWithoutIcons(
                    text = reminder.medicineName,
                    status = "Scheduled at $formattedTime",
                    onClick = {
                        // Handle reminder click
                    }
                )
            }
        }
    }
}
fun formatTime(time: String): String {
    return try {
        // Input format based on the example provided
        val inputFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
        val date = inputFormat.parse(time)

        // Determine the output format based on the hour of the day
        val outputFormat = if (date != null && date.hours in 13..23) {
            SimpleDateFormat("HH:mm", Locale.getDefault()) // 24-hour format
        } else {
            SimpleDateFormat("h:mm a", Locale.getDefault()) // 12-hour format with AM/PM
        }

        date?.let { outputFormat.format(it) } ?: time
    } catch (e: Exception) {
        time // If there's an issue, return the original string
    }
}



fun formatDayTitle(timeInMillis: Long): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeInMillis // Set the timeInMillis directly

    val dateFormat = SimpleDateFormat("EEEE, MMMM dd", Locale.getDefault())
    val today = Calendar.getInstance()

    return if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
        calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
        "Today - ${dateFormat.format(calendar.time)}"
    } else {
        dateFormat.format(calendar.time)
    }
}

@Composable
fun MedicationBreakCard(
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(156.dp),
        shape = RoundedCornerShape(36.dp),
        colors = CardDefaults.cardColors(
            containerColor = AliceBlue,
            contentColor = MSUGreen
        ),
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 25.dp)
                    .align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = stringResource(R.string.medication_break),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                )

                Text(
                    text = stringResource(R.string.no_medications_scheduled_for_this_date_take_a_break_and_relax),
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    }
}
