package com.example.medicalcareapp.screens

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicalcareapp.R
import com.example.medicalcareapp.composables.ButtonComponent
import com.example.medicalcareapp.event_manager.EventManager
import com.example.medicalcareapp.navigation.Screens
import com.example.medicalcareapp.ui.theme.DarkJungleGreen
import com.example.medicalcareapp.ui.theme.Honeydew
import com.example.medicalcareapp.ui.theme.LightOlivine
import com.example.medicalcareapp.ui.theme.LightTeaGreen
import com.example.medicalcareapp.ui.theme.SmokyBlack
import org.koin.compose.koinInject

@Composable
fun WelcomeScreen(
    navController: NavController,
    eventManager: EventManager = koinInject()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_rainbow),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 300.dp)
                    .background(
                        color = Honeydew,
                        shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.heart_component),
                        contentDescription = null
                    )
                    Spacer(Modifier.height(23.dp))
                    Text(
                        text = "Medical Care App",
                        color = DarkJungleGreen,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(198.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ButtonComponent(
                            onClick = { navController.navigate(Screens.Register.route) },
                            modifier = Modifier
                                .height(50.dp)
                                .width(131.dp)
                                .shadow(
                                    elevation = 8.dp,
                                    shape = CircleShape
                                ),
                            text = "Sign up",
                            isFilled = true,
                            fontSize = 20.sp,
                            contentColorChoice = SmokyBlack,
                            fillColorChoice = LightTeaGreen,
                        )
                        ButtonComponent(
                            onClick = { navController.navigate(Screens.Login.route) },
                            modifier = Modifier
                                .height(50.dp)
                                .width(131.dp)
                                .shadow(
                                    elevation = 8.dp,
                                    shape = CircleShape
                                ),
                            text = "Log in",
                            isFilled = true,
                            fontSize = 20.sp,
                            fillColorChoice = LightOlivine,
                        )
                    }
                }
            }
        }
    }
}