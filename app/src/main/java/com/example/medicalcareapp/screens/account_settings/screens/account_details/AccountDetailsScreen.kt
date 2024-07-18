package com.example.medicalcareapp.screens.account_settings.screens.account_details

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicalcareapp.R
import com.example.medicalcareapp.composables.ButtonComponent
import com.example.medicalcareapp.composables.GenericOutlinedTextFieldWithValue
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.ui.theme.BlackOlive
import com.example.medicalcareapp.ui.theme.HookersGreen
import com.example.medicalcareapp.ui.theme.LightBlue
import com.example.medicalcareapp.ui.theme.SmokyBlack

@Composable
fun AccountDetailsScreen(
    navController: NavController,
) {
    var isNavigationInProgress by remember { mutableStateOf(false) }

    var firstName by remember { mutableStateOf(TextFieldValue("")) }
    var lastName by remember { mutableStateOf(TextFieldValue("")) }
    var userPhoneNumber by remember { mutableStateOf(TextFieldValue("")) }
    var isFirstNameError by remember { mutableStateOf(false) }
    var isLastNameError by remember { mutableStateOf(false) }
    var isUserPhoneNumberError by remember { mutableStateOf(false) }

    var buttonText by remember { mutableStateOf("Edit Details") }
    var isEnabled by remember { mutableStateOf(false) }

    val focusRequesterFirstName = remember { FocusRequester() }
    val focusRequesterLastName = remember { FocusRequester() }
    val focusRequesterPhoneNumber = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        if (firstName.text.isEmpty() && lastName.text.isEmpty() && userPhoneNumber.text.isEmpty()) {
            buttonText = "Add Details"
            isEnabled = false
        } else {
            buttonText = "Edit Details"
            isEnabled = false
        }
    }

    fun updateButtonState() {
        buttonText = when {
            firstName.text.isEmpty() && lastName.text.isEmpty() && userPhoneNumber.text.isEmpty() -> "Cancel"
            firstName.text.isNotEmpty() && lastName.text.isNotEmpty() && userPhoneNumber.text.length == 10 -> "Save"
            else -> if (buttonText == "Add Details" || buttonText == "Edit Details") buttonText else "Save"
        }
        isEnabled = buttonText == "Save"
    }

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
            TextInputField(
                label = "First Name",
                hint = "Enter First Name",
                text = firstName,
                onTextChanged = {
                    firstName = it
                    isFirstNameError = firstName.text.isEmpty()
                    updateButtonState()
                },
                isEnabled = isEnabled,
                isErrorTextField = isFirstNameError,
                errorMessage = "First Name cannot be empty",
                focusRequester = focusRequesterFirstName
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextInputField(
                label = "Last Name",
                hint = "Enter Last Name",
                text = lastName,
                onTextChanged = {
                    lastName = it
                    isLastNameError = lastName.text.isEmpty()
                    updateButtonState()
                },
                isEnabled = isEnabled,
                isErrorTextField = isLastNameError,
                errorMessage = "Last Name cannot be empty",
                focusRequester = focusRequesterLastName
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextInputField(
                label = "Phone Number",
                hint = "Enter Phone Number",
                text = userPhoneNumber,
                onTextChanged = {
                    userPhoneNumber = it
                    isUserPhoneNumberError = it.text.length != 10
                    updateButtonState()
                },
                isEnabled = isEnabled,
                imeAction = ImeAction.Done,
                isErrorTextField = isUserPhoneNumberError,
                errorMessage = stringResource(R.string.phone_number_must_be_10_digits_long),
                keyboardType = KeyboardType.Number,
                focusRequester = focusRequesterPhoneNumber
            )
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(20.dp))
            ButtonComponent(
                onClick = {
                    when (buttonText) {
                        "Add Details" -> {
                            isEnabled = true
                            buttonText = if (firstName.text.isEmpty() && lastName.text.isEmpty() && userPhoneNumber.text.isEmpty()) "Cancel" else "Save"
                            if (buttonText == "Save") {
                                focusRequesterFirstName.requestFocus()
                            }
                        }

                        "Edit Details" -> {
                            isEnabled = true
                            buttonText = if (firstName.text.isEmpty() && lastName.text.isEmpty() && userPhoneNumber.text.isEmpty()) "Cancel" else "Save"
                            if (buttonText == "Save") {
                                focusRequesterFirstName.requestFocus()
                            }
                        }

                        "Save" -> {
                            if (firstName.text.isNotEmpty() && lastName.text.isNotEmpty() && userPhoneNumber.text.length == 10) {
                                isEnabled = false
                                buttonText = "Edit Details"
                                // Save the details here
                            }
                        }

                        "Cancel" -> {
                            isEnabled = false
                            firstName = TextFieldValue("")
                            lastName = TextFieldValue("")
                            userPhoneNumber = TextFieldValue("")
                            buttonText = "Add Details"
                        }

//                        "Delete" -> {
//                            isEnabled = false
//                            buttonText = "Add Number"
//                        }
                    }
                },
                modifier = Modifier
                    .height(50.dp)
                    .width(250.dp)
                    .align(Alignment.CenterHorizontally),
                text = buttonText,
                isFilled = true,
                fontSize = 20.sp,
                cornerRadius = 20,
                fillColorChoice = LightBlue,
                contentColorChoice = SmokyBlack,
                isDisabled = buttonText == "Save" && !(firstName.text.isNotEmpty() && lastName.text.isNotEmpty() && userPhoneNumber.text.length == 10)
            )
            Spacer(modifier = Modifier.height(114.dp))
        }
    }
    if (buttonText == "Cancel" || buttonText == "Save") {
        DisposableEffect(Unit) {
            focusRequesterFirstName.requestFocus()
            onDispose { }
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
            imageVector = Icons.Outlined.ArrowBack,
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
            text = stringResource(R.string.account_details),
            color = BlackOlive,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun TextInputField(
    label: String = "",
    hint: String = "",
    text: TextFieldValue,
    onTextChanged: (TextFieldValue) -> Unit,
    imeAction: ImeAction = ImeAction.Next,
    isEnabled: Boolean = true,
    errorMessage: String = "",
    isErrorTextField: Boolean = false,
    focusRequester: FocusRequester = FocusRequester(),
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column {
        Text(
            text = label,
            color = SmokyBlack,
            fontSize = 16.sp,
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(6.dp))
        GenericOutlinedTextFieldWithValue(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    if (it.isFocused && text.text.isEmpty()) {
                        focusRequester.requestFocus()
                    }
                },
            label = hint,
            initialValue = text,
            onValueChanged = onTextChanged,
            imeAction = imeAction,
            isEnabled = isEnabled,
            errorMessage = errorMessage,
            isErrorTextField = isErrorTextField,
            keyboardType = keyboardType
        )
    }
}