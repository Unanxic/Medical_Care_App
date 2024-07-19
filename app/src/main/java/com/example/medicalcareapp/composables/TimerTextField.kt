package com.example.medicalcareapp.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.medicalcareapp.R
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.ui.theme.EerieBlack
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerTextField(
    label: String = "Time",
    textFieldColors: AppColors = AppColors.DEFAULT,
    initialTime: LocalTime = LocalTime.now(),
    onTimeSelected: (LocalTime) -> Unit,
) {
    var selectedTime by rememberSaveable { mutableStateOf(initialTime) }
    var isTimeSelectOpen by remember { mutableStateOf(false) }
    var isButtonClicked by remember { mutableStateOf(false) }


    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")


    val timePickerState =
        rememberTimePickerState(initialHour = initialTime.hour, initialMinute = initialTime.minute)

    LaunchedEffect(timePickerState.hour, timePickerState.minute) {
        selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        readOnly = true,
        singleLine = true,
        value = selectedTime.format(timeFormatter),
        onValueChange = {},
        label = { Text(text = label) },
        trailingIcon = {
            Image(
                painter = painterResource(id = R.drawable.clock_ic),
                contentDescription = "Icon Button",
                colorFilter = ColorFilter.tint(if (isButtonClicked) EerieBlack else Color.White),
                modifier = Modifier.setNoRippleClickable {
                    isTimeSelectOpen = !isTimeSelectOpen
                    isButtonClicked = !isButtonClicked
                }
            )
        },
        colors = textFieldColors.colors.invoke(),
    )

    if (isTimeSelectOpen) {
        Spacer(Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            TimePicker(state = timePickerState)
        }
    }
}