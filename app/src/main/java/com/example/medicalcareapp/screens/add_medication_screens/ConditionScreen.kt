package com.example.medicalcareapp.screens.add_medication_screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalcareapp.R
import com.example.medicalcareapp.composables.ButtonComponent
import com.example.medicalcareapp.composables.GenericTextField
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.ui.theme.DarkJungleGreen
import com.example.medicalcareapp.ui.theme.Honeydew
import com.example.medicalcareapp.ui.theme.LightOlivine

@Composable
fun ConditionScreen() {

    var condition by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_rainbow),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        )
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .size(25.dp)
                .setNoRippleClickable {
                    //todo
                },
            tint = DarkJungleGreen
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 87.dp, bottom = 38.dp)
                .padding(horizontal = 35.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Image(
                painter = painterResource(id = R.drawable.red_cirle_with_flag),
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.what_are_you_taking_it_for),
                color = DarkJungleGreen,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
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
                    .padding(top = 250.dp)
                    .background(
                        color = Honeydew,
                        shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.TopCenter)
                        .imePadding()
                        .verticalScroll(rememberScrollState())
                        .padding(top = 66.dp)
                        .padding(horizontal = 32.dp)
                ) {
                    GenericTextField(
                        value = condition,
                        updateText = {
                            condition = it
                        },
                        fontSize = 18.sp,
                        imeAction = ImeAction.Done
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                    ) {
                        AnimatedVisibility(
                            visible = condition.isEmpty(),
                            enter = slideInHorizontally(
                                initialOffsetX = { -it },
                                animationSpec = tween(400)
                            ) + fadeIn(animationSpec = tween(400)),
                            exit = slideOutHorizontally(
                                targetOffsetX = { -it },
                                animationSpec = tween(400)
                            ) + fadeOut(animationSpec = tween(400))
                        ) {
                            Text(
                                text = stringResource(R.string.start_typing_your_condition),
                                color = DarkJungleGreen,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Light
                            )
                        }
                    }
                    Spacer(Modifier.weight(1f))
                    Spacer(Modifier.height(70.dp))
                    ButtonComponent(
                        onClick = { /* todo */ },
                        modifier = Modifier
                            .height(50.dp)
                            .width(250.dp)
                            .align(Alignment.CenterHorizontally),
                        text = stringResource(R.string.next),
                        isFilled = true,
                        fontSize = 20.sp,
                        cornerRadius = 20,
                        fillColorChoice = LightOlivine
                    )
                    Spacer(Modifier.height(70.dp))
                }
            }
        }
    }
}