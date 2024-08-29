package com.example.medicalcareapp.screens.account_settings.screens.sos_contact

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicalcareapp.R
import com.example.medicalcareapp.composables.ButtonComponent
import com.example.medicalcareapp.composables.GenericOutlinedTextFieldWithValue
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.navigation.Screens
import com.example.medicalcareapp.screens.account_settings.viewmodels.SOSContactViewModel
import com.example.medicalcareapp.ui.theme.DesaturatedCyan
import com.example.medicalcareapp.ui.theme.EerieBlack
import com.example.medicalcareapp.ui.theme.HookersGreen
import com.example.medicalcareapp.ui.theme.LightBlue
import com.example.medicalcareapp.ui.theme.SmokyBlack
import org.koin.androidx.compose.koinViewModel

@Composable
fun SOSContactScreen(
    navController: NavController,
    viewModel: SOSContactViewModel = koinViewModel()
) {
    var isNavigationInProgress by remember { mutableStateOf(false) }
    val sosContact by viewModel.sosContact.collectAsState()
    var sosPhoneNumber by remember { mutableStateOf(sosContact?.phoneNumber ?: "") }
    var isEnabled by remember { mutableStateOf(false) }
    var buttonText by remember { mutableStateOf("Edit Number") }
    var isErrorPhoneNumber by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(sosContact) {
        sosContact?.phoneNumber?.let {
            sosPhoneNumber = it
            isEnabled = false
        } ?: run {
            sosPhoneNumber = ""
            isEnabled = false
        }
    }

    val buttonTextResId = when {
        isEnabled && sosPhoneNumber.isEmpty() -> {
            if (sosContact != null) R.string.delete_uppercase else R.string.cancel_uppercase
        }

        sosPhoneNumber.isEmpty() -> R.string.add_number
        isEnabled -> R.string.save
        else -> R.string.edit_number
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(color = HookersGreen),
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(color = HookersGreen),
        ) {
            GreenRoundedTopBarWithIcon(onBackClick = {
                if (!isNavigationInProgress) {
                    isNavigationInProgress = true
                    navController.popBackStack()
                }
            })
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
            ) {
                InputField(
                    label = stringResource(R.string.sos_contact),
                    hint = "",
                    text = sosPhoneNumber,
                    onTextChanged = {
                        sosPhoneNumber = it
                        isErrorPhoneNumber = it.length != 10
                        buttonText = when {
                            isEnabled && sosPhoneNumber.isEmpty() -> {
                                if (sosContact != null) "Delete" else "Cancel"
                            }

                            sosPhoneNumber.isEmpty() -> "Add Number"
                            isEnabled -> "Save"
                            else -> "Edit Number"
                        }
                    },
                    imeAction = ImeAction.Done,
                    isEnabled = isEnabled,
                    errorMessage = stringResource(R.string.phone_number_must_be_10_digits_long),
                    isErrorTextField = isErrorPhoneNumber,
                    focusRequester = focusRequester
                )
                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.height(20.dp))
                ButtonComponent(
                    onClick = {
                        when (buttonTextResId) {
                            R.string.add_number -> {
                                isEnabled = true
                            }

                            R.string.edit_number -> {
                                isEnabled = true
                            }

                            R.string.save -> {
                                if (sosPhoneNumber.length == 10) {
                                    viewModel.saveSOSContact(sosPhoneNumber) {
                                        isEnabled = false
                                        navController.navigate(Screens.SOSContactSuccess.route) {
                                            popUpTo(0) { inclusive = true }
                                        }
                                    }
                                }
                            }

                            R.string.cancel_uppercase -> {
                                isEnabled = false
                                sosPhoneNumber = sosContact?.phoneNumber ?: ""
                            }

                            R.string.delete_uppercase -> {
                                viewModel.deleteSOSContact {
                                    isEnabled = false
                                    sosPhoneNumber = ""
                                    navController.navigate(Screens.SOSContactSuccessDelete.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
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
                    isDisabled = buttonTextResId == R.string.save && sosPhoneNumber.length != 10
                )
                Spacer(modifier = Modifier.height(114.dp))
            }
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
fun InputField(
    label: String = "",
    hint: String = "",
    text: String,
    onTextChanged: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Next,
    isEnabled: Boolean = true,
    errorMessage: String = "",
    isErrorTextField: Boolean = false,
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
            isErrorTextField = isErrorTextField
        )
    }
}

@Composable
fun GreenRoundedTopBarWithIcon(
    onBackClick: () -> Unit
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(DesaturatedCyan)
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.rectangle_green_rounded),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                colorFilter = ColorFilter.tint(DesaturatedCyan)
            )
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(horizontal = 31.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.sos_contact),
                    color = EerieBlack,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.add_or_change_the_number_for_the_sos_contact),
                    color = EerieBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Image(
                    painter = painterResource(id = R.drawable.sos_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(170.dp)
                )
            }
        }
    }
}
