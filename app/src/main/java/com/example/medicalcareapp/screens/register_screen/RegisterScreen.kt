package com.example.medicalcareapp.screens.register_screen

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
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.medicalcareapp.R
import com.example.medicalcareapp.composables.ButtonComponent
import com.example.medicalcareapp.composables.GenericFilledTextField
import com.example.medicalcareapp.extesions.medicineNavigateSingleTop
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.navigation.Screens
import com.example.medicalcareapp.ui.theme.AliceBlue
import com.example.medicalcareapp.ui.theme.EerieBlack
import com.example.medicalcareapp.ui.theme.LilacPurple
import com.example.medicalcareapp.ui.theme.MSUGreen
import com.example.medicalcareapp.ui.theme.PewterBlue
import com.example.medicalcareapp.ui.theme.SmokyBlack

@Composable
fun RegisterScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PewterBlue),
    ) {
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
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
                .padding(top = 87.dp, bottom = 38.dp),
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
                        text = stringResource(R.string.let_s_sign_you_up),
                        color = EerieBlack,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = stringResource(R.string.hello_and_welcome),
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
                            },
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
                            },
                        )
                    }
                    Spacer(Modifier.height(20.dp))
                    Column {
                        Text(
                            text = "Confirm password",
                            color = EerieBlack,
                            fontSize = 16.sp,
                        )
                        GenericFilledTextField(
                            value = confirmPassword,
                            title = "",
                            updateText = {
                                confirmPassword = it
                            },
                            keyboardType = KeyboardType.Password,
                            showTrailingIcon = true,
                            imeAction = ImeAction.Done
                        )
                    }
                    Spacer(Modifier.height(40.dp))
                    ButtonComponent(
                        onClick = { /* todo */ },
                        modifier = Modifier
                            .height(50.dp)
                            .width(250.dp)
                            .align(Alignment.CenterHorizontally),
                        text = stringResource(R.string.sign_up),
                        isFilled = true,
                        isDisabled = email.isBlank() || password.isBlank() || confirmPassword.isBlank(),
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
                        LoginLink(navController = navController)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun LoginLink(navController: NavController) {
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
                text = stringResource(R.string.already_have_an_account),
                fontSize = 16.sp,
                color = EerieBlack.copy(alpha = 0.8f),
                fontWeight = FontWeight.Light
            )
            Text(
                text = stringResource(R.string.log_in),
                color = LilacPurple,
                fontSize = 16.sp,
                modifier = Modifier.setNoRippleClickable {
                    navController.medicineNavigateSingleTop(Screens.Login.route)
                }
            )
        }
    }
}