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
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicalcareapp.R
import com.example.medicalcareapp.composables.ButtonComponent
import com.example.medicalcareapp.composables.GenericOutlinedTextFieldWithValue
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.navigation.Screens
import com.example.medicalcareapp.screens.account_settings.screens.account_details.viewmodel.AccountDetailsViewModel
import com.example.medicalcareapp.ui.theme.BlackOlive
import com.example.medicalcareapp.ui.theme.HookersGreen
import com.example.medicalcareapp.ui.theme.LightBlue
import com.example.medicalcareapp.ui.theme.SmokyBlack
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountDetailsScreen(
    navController: NavController,
    viewModel: AccountDetailsViewModel = koinViewModel()
) {
    var isNavigationInProgress by remember { mutableStateOf(false) }
    val userDetails by viewModel.userDetails.collectAsState()
    var firstName by remember { mutableStateOf(userDetails?.firstName ?: "") }
    var lastName by remember { mutableStateOf(userDetails?.lastName ?: "") }
    var phoneNumber by remember { mutableStateOf(userDetails?.phoneNumber ?: "") }
    var isEnabled by remember { mutableStateOf(false) }
    var isErrorFirstName by remember { mutableStateOf(false) }
    var isErrorLastName by remember { mutableStateOf(false) }
    var isErrorPhoneNumber by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(userDetails) {
        userDetails?.let {
            firstName = it.firstName
            lastName = it.lastName
            phoneNumber = it.phoneNumber
            isEnabled = false
        } ?: run {
            firstName = ""
            lastName = ""
            phoneNumber = ""
            isEnabled = false
        }
    }

    val buttonTextResId = when {
        isEnabled && firstName.isEmpty() && lastName.isEmpty() && phoneNumber.isEmpty() -> {
            if (userDetails != null) R.string.delete_uppercase else R.string.cancel_uppercase
        }
        firstName.isEmpty() && lastName.isEmpty() && phoneNumber.isEmpty() -> R.string.add_details
        isEnabled -> R.string.save
        else -> R.string.edit_details
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(color = HookersGreen)
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
                label = stringResource(R.string.first_name),
                hint = stringResource(R.string.enter_first_name),
                text = firstName,
                onTextChanged = {
                    firstName = it
                    isErrorFirstName = it.isEmpty() && isEnabled
                },
                imeAction = ImeAction.Next,
                isEnabled = isEnabled,
                errorMessage = stringResource(R.string.first_name_empty_error),
                isErrorTextField = isErrorFirstName,
                focusRequester = focusRequester // Focus on this field first
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextInputField(
                label = stringResource(R.string.last_name),
                hint = stringResource(R.string.enter_last_name),
                text = lastName,
                onTextChanged = {
                    lastName = it
                    isErrorLastName = it.isEmpty() && isEnabled
                },
                imeAction = ImeAction.Next,
                isEnabled = isEnabled,
                errorMessage = stringResource(R.string.last_name_empty_error),
                isErrorTextField = isErrorLastName
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextInputField(
                label = stringResource(R.string.phone_number),
                hint = stringResource(R.string.enter_phone_number),
                text = phoneNumber,
                onTextChanged = {
                    phoneNumber = it
                    isErrorPhoneNumber = it.length != 10 && isEnabled
                },
                imeAction = ImeAction.Done,
                isEnabled = isEnabled,
                errorMessage = stringResource(R.string.phone_number_must_be_10_digits_long),
                isErrorTextField = isErrorPhoneNumber,
                keyboardType = KeyboardType.Number
            )

            Spacer(modifier = Modifier.weight(1f))

            Spacer(modifier = Modifier.height(20.dp))

            ButtonComponent(
                onClick = {
                    when (buttonTextResId) {
                        R.string.add_details, R.string.edit_details -> {
                            isEnabled = true
                        }

                        R.string.save -> {
                            val isFirstNameValid = firstName.isNotEmpty()
                            val isLastNameValid = lastName.isNotEmpty()
                            val isPhoneNumberValid = phoneNumber.length == 10

                            if (isFirstNameValid && isLastNameValid && isPhoneNumberValid) {
                                viewModel.saveUserDetails(
                                    firstName,
                                    lastName,
                                    phoneNumber
                                )
                                isEnabled = false
                                isErrorFirstName = false
                                isErrorLastName = false
                                isErrorPhoneNumber = false

                                // Navigate to the success screen after saving
                                navController.navigate(Screens.DetailsSuccess.route) {
                                    popUpTo(0) { inclusive = true }
                                }
                            } else {
                                // Update the error states if validation fails
                                isErrorFirstName = !isFirstNameValid
                                isErrorLastName = !isLastNameValid
                                isErrorPhoneNumber = !isPhoneNumberValid
                            }
                        }

                        R.string.cancel_uppercase -> {
                            isEnabled = false
                            firstName = userDetails?.firstName ?: ""
                            lastName = userDetails?.lastName ?: ""
                            phoneNumber = userDetails?.phoneNumber ?: ""
                            isErrorFirstName = false
                            isErrorLastName = false
                            isErrorPhoneNumber = false
                        }

                        R.string.delete_uppercase -> {
                            viewModel.deleteUserDetails()
                            isEnabled = false
                            firstName = ""
                            lastName = ""
                            phoneNumber = ""

                            // Navigate to the delete success screen after deleting
                            navController.navigate(Screens.DetailsSuccessDelete.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .height(50.dp)
                    .width(250.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = buttonTextResId),
                isFilled = true,
                fontSize = 20.sp,
                cornerRadius = 20,
                fillColorChoice = LightBlue,
                contentColorChoice = SmokyBlack,
                isDisabled = buttonTextResId == R.string.save && (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.length != 10)
            )

            Spacer(modifier = Modifier.height(114.dp))
        }
    }
    if (buttonTextResId == R.string.cancel_uppercase || buttonTextResId == R.string.save || buttonTextResId == R.string.delete_uppercase) {
        DisposableEffect(Unit) {
            focusRequester.requestFocus()
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
    text: String,
    onTextChanged: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Next,
    isEnabled: Boolean = true,
    errorMessage: String = "",
    isErrorTextField: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    focusRequester: FocusRequester = FocusRequester()
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
                .focusRequester(focusRequester),
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