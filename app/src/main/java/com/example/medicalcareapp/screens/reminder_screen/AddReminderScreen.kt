package com.example.medicalcareapp.screens.reminder_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicalcareapp.R
import com.example.medicalcareapp.composables.ButtonComponent
import com.example.medicalcareapp.composables.DateTextField
import com.example.medicalcareapp.composables.DropDownField
import com.example.medicalcareapp.composables.TimerTextField
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.navigation.Screens
import com.example.medicalcareapp.screens.reminder_screen.viewmodel.ReminderViewModel
import com.example.medicalcareapp.ui.theme.BlackOlive
import com.example.medicalcareapp.ui.theme.HookersGreen
import com.example.medicalcareapp.ui.theme.LightBlue
import com.example.medicalcareapp.ui.theme.SmokyBlack
import com.example.medicalcareapp.utilities.Recurrence
import org.koin.compose.koinInject
import java.time.LocalTime
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddReminderScreen(
    navController: NavController,
    medicationName: String,
    viewModel: ReminderViewModel = koinInject()
) {
    val context = LocalContext.current
    var isNavigationInProgress by remember { mutableStateOf(false) }

    var recurrence by rememberSaveable { mutableStateOf(Recurrence.Daily.name) }
    var startDate by rememberSaveable { mutableLongStateOf(Date().time) }
    var endDate by rememberSaveable { mutableLongStateOf(Date().time) }

    var selectedTime by rememberSaveable { mutableStateOf(LocalTime.now()) }
    var additionalTimes by rememberSaveable { mutableStateOf(listOf<LocalTime>()) }

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
                text = stringResource(R.string.recurrence),
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
                text = stringResource(R.string.start_date),
                color = SmokyBlack,
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(6.dp))
            DateTextField(
                initialDate = startDate,
                onDateSelected = { startDate = it }
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.end_date),
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
                text = stringResource(R.string.time_s_for_medication),
                color = SmokyBlack,
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(6.dp))
            TimerTextField(
                initialTime = selectedTime,
                onTimeSelected = { selectedTime = it }
            )
            additionalTimes.forEachIndexed { index, time ->
                Spacer(modifier = Modifier.height(11.dp))
                TimerTextField(
                    initialTime = time,
                    onTimeSelected = { newTime ->
                        additionalTimes = additionalTimes.toMutableList().apply { set(index, newTime) }
                    },
                    showDeleteIcon = true,
                    onDeleteClick = {
                        additionalTimes = additionalTimes.toMutableList().apply { removeAt(index) }
                    }
                )
            }
            if (additionalTimes.size < 3) {
                AddMoreButton(
                    text = stringResource(R.string.add_time),
                    onClick = { additionalTimes = additionalTimes + LocalTime.now() },
                    iconResourceId = R.drawable.add
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(12.dp))
            ButtonComponent(
                onClick = {
                    val timeOne = selectedTime.toString()
                    val timeTwo = additionalTimes.getOrNull(0)?.toString()
                    val timeThree = additionalTimes.getOrNull(1)?.toString()
                    val timeFour = additionalTimes.getOrNull(2)?.toString()

                    viewModel.setReminderData(
                        medicineName = medicationName,
                        recurrence = recurrence,
                        startDate = startDate,
                        endDate = endDate,
                        timeOne = timeOne,
                        timeTwo = timeTwo,
                        timeThree = timeThree,
                        timeFour = timeFour
                    )

                    viewModel.saveReminder(context)
                    navController.navigate(Screens.SuccessfulAddReminder.route) {
                        popUpTo(0)
                    }
                },
                modifier = Modifier
                    .height(50.dp)
                    .width(250.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(R.string.add_reminder),
                isFilled = true,
                fontSize = 20.sp,
                cornerRadius = 20,
                fillColorChoice = LightBlue,
                contentColorChoice = SmokyBlack,
            )
            Spacer(modifier = Modifier.height(32.dp))
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
            text = stringResource(R.string.add_reminder),
            color = BlackOlive,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun AddMoreButton(
    text: String,
    fontSize: TextUnit = 14.sp,
    onClick: () -> Unit = {},
    iconResourceId: Int
) {
    Row(
        modifier = Modifier.setNoRippleClickable(onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(iconResourceId),
            contentDescription = "Plus Icon"
        )
        Spacer(Modifier.width(13.dp))
        Text(
            text = text,
            fontSize = fontSize,
            color = Color.White
        )
    }
}