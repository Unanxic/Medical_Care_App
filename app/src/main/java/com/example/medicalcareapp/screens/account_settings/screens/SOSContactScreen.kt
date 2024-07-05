package com.example.medicalcareapp.screens.account_settings.screens

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
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.medicalcareapp.composables.GenericOutlinedTextField
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.ui.theme.DesaturatedCyan
import com.example.medicalcareapp.ui.theme.EerieBlack
import com.example.medicalcareapp.ui.theme.HookersGreen
import com.example.medicalcareapp.ui.theme.LightBlue
import com.example.medicalcareapp.ui.theme.SmokyBlack
import kotlinx.coroutines.launch

@Composable
fun SOSContactScreen(
    navController: NavController,
) {
    var isNavigationInProgress by remember { mutableStateOf(false) }
    var sosPhoneNumber by remember { mutableStateOf("") }
    var isEnabled by remember { mutableStateOf(false) }
    var buttonText by remember { mutableStateOf("Edit Number") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var isErrorPhoneNumber by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

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
                    hint = stringResource(R.string.ex_69),
                    text = sosPhoneNumber,
                    onTextChanged = {
                        sosPhoneNumber = it
                        isErrorPhoneNumber = it.length != 10
                        buttonText = if (it.isEmpty()) "Cancel" else "Save"
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
                        when (buttonText) {
                            "Edit Number" -> {
                                isEnabled = true
                                buttonText = if (sosPhoneNumber.isEmpty()) "Cancel" else "Save"
                            }

                            "Save" -> {
                                if (sosPhoneNumber.length == 10) {
                                    isEnabled = false
                                    buttonText = "Edit Number"
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Saved Successfully")
                                    }
                                }
                            }

                            "Cancel" -> {
                                isEnabled = false
                                buttonText = "Edit Number"
                                sosPhoneNumber = ""
                            }
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
                    isDisabled = buttonText == "Save" && sosPhoneNumber.length != 10
                )
                Spacer(modifier = Modifier.height(114.dp))
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        )
    }
    if (buttonText == "Cancel" || buttonText == "Save") {
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
    text: String = "",
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
        GenericOutlinedTextField(
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