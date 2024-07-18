package com.example.medicalcareapp.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.medicalcareapp.extesions.toFormattedDateString
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTextField(
    label: String = "",
    textFieldColors: AppColors = AppColors.DEFAULT,
    initialDate: Long = System.currentTimeMillis(),
    onDateSelected: (Long) -> Unit,
    isDateSelectable: (Long) -> Boolean = { true }  // Custom validation function
) {
    val today = Calendar.getInstance()
    today.set(Calendar.HOUR_OF_DAY, 0)
    today.set(Calendar.MINUTE, 0)
    today.set(Calendar.SECOND, 0)
    today.set(Calendar.MILLISECOND, 0)
    val currentDayMillis = today.timeInMillis

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate
    )

    var selectedDate by rememberSaveable {
        mutableStateOf(
            datePickerState.selectedDateMillis?.toFormattedDateString() ?: ""
        )
    }

    var shouldDisplay by remember { mutableStateOf(false) }

    DatePickerDialog(
        state = datePickerState,
        shouldDisplay = shouldDisplay,
        onConfirmClicked = { selectedDateInMillis ->
            selectedDate = selectedDateInMillis.toFormattedDateString()
            onDateSelected(selectedDateInMillis)
            shouldDisplay = false
        },
        dismissRequest = {
            shouldDisplay = false
        }
    )

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        readOnly = true,
        singleLine = true,
        value = selectedDate,
        onValueChange = {},
        label = { Text(text = label) },
        trailingIcon = {
            IconButton(onClick = { shouldDisplay = true }) {
                Icon(
                    Icons.Default.DateRange, contentDescription = null,
                    tint = Color.White
                )
            }
        },
        colors = textFieldColors.colors.invoke(),
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    state: DatePickerState,
    shouldDisplay: Boolean,
    onConfirmClicked: (selectedDateInMillis: Long) -> Unit,
    dismissRequest: () -> Unit
) {
    if (shouldDisplay) {
        DatePickerDialog(
            onDismissRequest = dismissRequest,
            confirmButton = {
                Button(
                    modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp),
                    onClick = {
                        state.selectedDateMillis?.let {
                            onConfirmClicked(it)
                        }
                        dismissRequest()
                    }
                ) {
                    Text(text = "Okay")
                }
            },
            dismissButton = {
                TextButton(onClick = dismissRequest) {
                    Text(
                        text = "Cancel",
                    )
                }
            },
            content = {
                DatePicker(
                    state = state,
                    showModeToggle = false,
                    headline = {
                        state.selectedDateMillis?.toFormattedDateString()?.let {
                            Text(
                                modifier = Modifier.padding(start = 16.dp),
                                text = it
                            )
                        }
                    }
                )
            }
        )
    }
}