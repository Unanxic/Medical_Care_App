package com.example.medicalcareapp.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalcareapp.R
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.ui.theme.EerieBlack
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerTextField(
    textFieldColors: AppColors = AppColors.DEFAULT,
    initialTime: LocalTime = LocalTime.now(),
    onTimeSelected: (LocalTime) -> Unit,
    showDeleteIcon: Boolean = false,
    onDeleteClick: (() -> Unit)? = null
) {
    var selectedTime by rememberSaveable { mutableStateOf(initialTime) }
    var isTimeSelectOpen by remember { mutableStateOf(false) }
    var isButtonClicked by remember { mutableStateOf(false) }

    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")

    val timePickerState =
        rememberTimePickerState(initialHour = initialTime.hour, initialMinute = initialTime.minute)

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        readOnly = true,
        singleLine = true,
        value = selectedTime.format(timeFormatter),
        onValueChange = {},
        trailingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.clock_ic),
                    contentDescription = "Icon Button",
                    colorFilter = ColorFilter.tint(if (isButtonClicked) EerieBlack else Color.White),
                    modifier = Modifier.setNoRippleClickable {
                        isTimeSelectOpen = !isTimeSelectOpen
                        isButtonClicked = !isButtonClicked
                    }
                )
                Spacer(modifier = Modifier.width(10.dp))
                if (showDeleteIcon && onDeleteClick != null) {
                    Image(
                        painter = painterResource(id = R.drawable.trash_can),
                        contentDescription = "Delete Icon",
                        colorFilter = ColorFilter.tint(EerieBlack),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .setNoRippleClickable {
                                onDeleteClick()
                            }
                    )
                }
            }
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
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TimePicker(state = timePickerState)
                Spacer(Modifier.height(16.dp))
                Row {
                    TextButton(
                        onClick = {
                            selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                            onTimeSelected(selectedTime)
                            isTimeSelectOpen = false
                            isButtonClicked = false
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.confirm),
                            color = EerieBlack,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Spacer(Modifier.width(16.dp))
                    TextButton(
                        onClick = {
                            isTimeSelectOpen = false
                            isButtonClicked = false
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            color = EerieBlack,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}