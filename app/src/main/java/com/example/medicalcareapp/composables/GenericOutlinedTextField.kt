package com.example.medicalcareapp.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalcareapp.ui.theme.ArtyClickRed
import com.example.medicalcareapp.ui.theme.PewterBlue

@Composable
fun GenericOutlinedTextField(
    modifier: Modifier = Modifier,
    label: String,
    initialValue: String = "",
    onValueChanged: (String) -> Unit,
    readOnly: Boolean = false,
    textFieldColors: AppColors = AppColors.DEFAULT,
    imeAction: ImeAction = ImeAction.Next,
    errorMessage: String = "",
    isErrorTextField: Boolean = false,
    isEnabled: Boolean = true,
    focusRequester: FocusRequester = FocusRequester()

) {
    var textFieldValue by rememberSaveable(initialValue) { mutableStateOf(initialValue) }
    val focusManager = LocalFocusManager.current
    val isFocused = remember { mutableStateOf(false) }

    val errorVisible = isErrorTextField && (!isFocused.value && initialValue.isNotEmpty())

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusEvent { focusState ->
                isFocused.value = focusState.hasFocus
            },
        value = textFieldValue,
        onValueChange = { textFieldValue = it; onValueChanged(it) },
        singleLine = true,
        placeholder = {
            Text(
                text = label,
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 16.sp,
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                if (imeAction == ImeAction.Done) {
                    focusManager.clearFocus()
                }
            }
        ),
        shape = RoundedCornerShape(4.dp),
        readOnly = readOnly,
        colors = textFieldColors.colors.invoke(),
        enabled = isEnabled
    )
    if (errorVisible) {
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = errorMessage,
            color = ArtyClickRed,
            fontSize = 12.sp
        )
    }
}

enum class AppColors(val colors: @Composable () -> TextFieldColors) {
    DEFAULT(
        colors = {
            OutlinedTextFieldDefaults.colors(
                // Generic
                cursorColor = PewterBlue,
                disabledContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                // Unfocused
                unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                unfocusedLabelColor = Color.White.copy(alpha = 0.2f),
                unfocusedPlaceholderColor = Color.White.copy(alpha = 0.2f),
                unfocusedTextColor = Color.White,
                // Focused
                focusedBorderColor = Color.White.copy(alpha = 0.2f),
                focusedLabelColor = Color.White,
                focusedPlaceholderColor = Color.White,
                focusedTextColor = Color.White,
                // Disabled
                disabledBorderColor = Color.White.copy(alpha = 0.2f),
                disabledLabelColor = Color.White.copy(alpha = 0.2f),
                disabledPlaceholderColor = Color.White.copy(alpha = 0.2f),
                disabledTextColor = Color.White.copy(alpha = 0.2f),
            )
        }
    ),
}