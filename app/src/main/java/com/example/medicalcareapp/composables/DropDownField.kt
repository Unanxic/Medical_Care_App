package com.example.medicalcareapp.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.toSize
import com.example.medicalcareapp.R
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.utilities.Recurrence
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownField(
    textFieldColors: AppColors = AppColors.DEFAULT,
    options: List<String> = listOf(
        Recurrence.Daily.name,
        Recurrence.Weekly.name,
        Recurrence.Monthly.name
    ),
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }
    val scope = rememberCoroutineScope()
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    Box {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
            readOnly = true,
            singleLine = true,
            value = selectedOption,
            onValueChange = {},
            trailingIcon = {
                Image(
                    painter = painterResource(
                        id = if (expanded) R.drawable.arrow_up_filled else R.drawable.arrow_down_filled
                    ),
                    contentDescription = null,
                    modifier = Modifier
                    .setNoRippleClickable { expanded = !expanded },
                    colorFilter = ColorFilter.tint(Color.White),
                )
            },
            colors = textFieldColors.colors.invoke(),
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedOption = option
                        expanded = false
                        scope.launch {
                            onItemSelected(option)
                        }
                    }
                )
            }
        }
    }
}
