package com.example.medicalcareapp.screens.welcome_screen

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicalcareapp.R
import com.example.medicalcareapp.composables.ButtonComponent
import com.example.medicalcareapp.event_manager.EventManager
import com.example.medicalcareapp.extesions.medicineNavigateSingleTop
import com.example.medicalcareapp.navigation.Screens
import com.example.medicalcareapp.ui.theme.AliceBlue
import com.example.medicalcareapp.ui.theme.EerieBlack
import com.example.medicalcareapp.ui.theme.MSUGreen
import com.example.medicalcareapp.ui.theme.PewterBlue
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
            .background(PewterBlue)
    ) {
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
                        color = AliceBlue,
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
                        text = stringResource(R.string.app_name),
                        color = EerieBlack,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.weight(1f))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ButtonComponent(
                            onClick = { navController.medicineNavigateSingleTop(Screens.Register.route) },
                            modifier = Modifier
                                .height(50.dp)
                                .width(131.dp)
                                .shadow(
                                    elevation = 8.dp,
                                    shape = CircleShape
                                ),
                            text = stringResource(R.string.sign_up),
                            isFilled = true,
                            fontSize = 16.sp,
                            contentColorChoice = SmokyBlack,
                            fillColorChoice = PewterBlue,
                        )
                        ButtonComponent(
                            onClick = { navController.medicineNavigateSingleTop(Screens.Login.route) },
                            modifier = Modifier
                                .height(50.dp)
                                .width(131.dp)
                                .shadow(
                                    elevation = 8.dp,
                                    shape = CircleShape
                                ),
                            text = stringResource(R.string.log_in),
                            isFilled = true,
                            fontSize = 16.sp,
                            fillColorChoice = MSUGreen,
                        )
                    }
                    Spacer(Modifier.height(51.dp))
                }
            }
        }
    }
}