package com.example.medicalcareapp.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.medicalcareapp.extesions.setNoRippleClickable
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.example.medicalcareapp.R
import com.example.medicalcareapp.ui.theme.PewterBlue
import com.example.medicalcareapp.ui.theme.SmokyBlack
import java.util.regex.Pattern

@OptIn(ExperimentalLayoutApi::class, ExperimentalComposeUiApi::class)
@Composable
fun GenericOutlinedTextField(
    modifier: Modifier = Modifier,
    label: String,
    initialValue: String = "",
    onValueChanged: (String) -> Unit,
    readOnly: Boolean = false,
    textFieldColors: AppColors = AppColors.DEFAULT,
    imeAction: ImeAction = ImeAction.Next
) {
    var textFieldValue by rememberSaveable(initialValue) { mutableStateOf(initialValue) }
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = textFieldValue,
        onValueChange = { textFieldValue = it; onValueChanged(it) },
        singleLine = true,
        placeholder = {
            Text(
                text = label,
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 16.sp,
            )},
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
        colors = textFieldColors.colors.invoke()
    )
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