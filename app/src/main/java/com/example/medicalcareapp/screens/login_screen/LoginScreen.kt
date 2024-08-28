package com.example.medicalcareapp.screens.login_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.domain.utils.FlowError
import com.example.domain.utils.FlowLoading
import com.example.domain.utils.FlowSuccess
import com.example.domain.utils.isLoggedIn
import com.example.medicalcareapp.R
import com.example.medicalcareapp.composables.ButtonComponent
import com.example.medicalcareapp.composables.GenericFilledTextField
import com.example.medicalcareapp.composables.alert_dialogs.AlertDialogs
import com.example.medicalcareapp.composables.alert_dialogs.DialogState
import com.example.medicalcareapp.extesions.isEmailValid
import com.example.medicalcareapp.extesions.medicineNavigateSingleTop
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.managers.LoaderManager
import com.example.medicalcareapp.navigation.Screens
import com.example.medicalcareapp.screens.login_screen.viewmodel.LoginViewModel
import com.example.medicalcareapp.ui.theme.AliceBlue
import com.example.medicalcareapp.ui.theme.EerieBlack
import com.example.medicalcareapp.ui.theme.LilacPurple
import com.example.medicalcareapp.ui.theme.MSUGreen
import com.example.medicalcareapp.ui.theme.PewterBlue
import com.example.medicalcareapp.ui.theme.SmokyBlack
import org.koin.compose.koinInject

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = koinInject(),
    loaderManager: LoaderManager = koinInject()
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(DialogState.NONE) }
    var isEmailError by rememberSaveable { mutableStateOf(false) }
    var isPasswordError by rememberSaveable { mutableStateOf(false) }

    val accountState by loginViewModel.authState.collectAsState()

    var isLoading by remember { mutableStateOf(false) }


    LaunchedEffect(accountState) {
        when (val account = accountState) {
            is FlowError -> {
                isLoading = false
                loaderManager.hide()
                showDialog = DialogState.INVALID_LOGIN
            }

            is FlowLoading -> {
                isLoading = true
                loaderManager.show()
            }

            is FlowSuccess -> {
                isLoading = false
                loaderManager.hide()
                account.data?.let {
                    navController.medicineNavigateSingleTop(Screens.Home.route)
                }
            }
        }
    }

    LaunchedEffect(accountState) {
        if (accountState.isLoggedIn()) {
            navController.popBackStack(Screens.Home.route, inclusive = false)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PewterBlue),
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .size(25.dp)
                .setNoRippleClickable {
                    navController.popBackStack(Screens.Welcome.route, inclusive = false)
                },
            tint = SmokyBlack
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 87.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_pill),
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 230.dp)
                    .background(
                        color = AliceBlue,
                        shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .imePadding()
                        .verticalScroll(rememberScrollState())
                        .padding(top = 66.dp)
                        .padding(horizontal = 32.dp)
                ) {
                    Text(
                        text = stringResource(R.string.let_s_sign_you_in),
                        color = EerieBlack,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = stringResource(R.string.welcome_back),
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.alpha(0.56f)
                    )
                    Spacer(Modifier.height(49.dp))
                    Column {
                        Text(
                            text = stringResource(R.string.email),
                            color = EerieBlack,
                            fontSize = 16.sp,
                        )
                        GenericFilledTextField(
                            value = email,
                            title = "",
                            updateText = {
                                email = it
                                isEmailError = !it.isEmailValid()
                            },
                            isErrorTextField = isEmailError,
                            errorMessage = stringResource(R.string.invalid_email_address)
                        )
                    }
                    Spacer(Modifier.height(20.dp))
                    Column {
                        Text(
                            text = stringResource(R.string.password),
                            color = EerieBlack,
                            fontSize = 16.sp,
                        )
                        GenericFilledTextField(
                            value = password,
                            title = "",
                            updateText = {
                                password = it
                                isPasswordError = it.length < 8
                            },
                            isErrorTextField = isPasswordError,
                            errorMessage = stringResource(R.string.password_length_is_less_than_8),
                            keyboardType = KeyboardType.Password,
                            showTrailingIcon = true,
                            imeAction = ImeAction.Done
                        )
                    }
                    Spacer(Modifier.height(40.dp))
                    ButtonComponent(
                        onClick = {
                            loginViewModel.loginWithMailAndPass(
                                username = email,
                                password = password
                            )
                        },
                        modifier = Modifier
                            .height(50.dp)
                            .width(250.dp)
                            .align(Alignment.CenterHorizontally),
                        text = stringResource(R.string.log_in),
                        isFilled = true,
                        isDisabled = email.isBlank() || password.isBlank() || isEmailError || isPasswordError,
                        fontSize = 16.sp,
                        cornerRadius = 20,
                        fillColorChoice = MSUGreen
                    )
                    Spacer(Modifier.height(10.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        RegisterLink(navController = navController)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MSUGreen)
            }
        }
    }
    AlertDialogs(
        showDialog = showDialog,
        closeDialog = {
            showDialog = DialogState.NONE
        }
    )
}


@Composable
fun RegisterLink(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.don_t_have_an_account),
                fontSize = 16.sp,
                color = EerieBlack.copy(alpha = 0.8f),
                fontWeight = FontWeight.Light
            )
            Text(
                text = stringResource(R.string.sign_up),
                color = LilacPurple,
                fontSize = 16.sp,
                modifier = Modifier.setNoRippleClickable {
                    navController.medicineNavigateSingleTop(Screens.Register.route)
                }
            )
        }
    }
}