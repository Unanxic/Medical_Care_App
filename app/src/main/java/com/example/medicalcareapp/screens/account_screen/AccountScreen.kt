package com.example.medicalcareapp.screens.account_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicalcareapp.R
import com.example.medicalcareapp.composables.alert_dialogs.LogoutDialogState
import com.example.medicalcareapp.event_manager.EventManager
import com.example.medicalcareapp.extesions.medicineNavigateSingleTop
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.managers.HomeScreenManager
import com.example.medicalcareapp.navigation.Screens
import com.example.medicalcareapp.screens.account_screen.viewmodel.AccountViewModel
import com.example.medicalcareapp.screens.account_settings.screens.account_details.viewmodel.AccountDetailsViewModel
import com.example.medicalcareapp.screens.account_settings.viewmodels.SOSContactViewModel
import com.example.medicalcareapp.ui.theme.AliceBlue
import com.example.medicalcareapp.ui.theme.DesaturatedCyan
import com.example.medicalcareapp.ui.theme.EerieBlack
import com.example.medicalcareapp.ui.theme.HookersGreen
import com.example.medicalcareapp.ui.theme.SmokyBlack
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun AccountScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    eventManager: EventManager = koinInject(),
    sosViewModel: SOSContactViewModel = koinViewModel(),
    accountViewModel: AccountViewModel = koinViewModel(),
    userDetailsViewModel: AccountDetailsViewModel = koinViewModel(),
    homeScreenManager: HomeScreenManager = koinInject()
) {
    val sosContact by sosViewModel.sosContact.collectAsState()
    var dialogState by remember { mutableStateOf(LogoutDialogState.NONE) }
    val userDetails by userDetailsViewModel.userDetails.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
            .background(color = HookersGreen)
            .padding(top = paddingValues.calculateTopPadding()),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.rectangle_green_rounded),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                colorFilter = ColorFilter.tint(DesaturatedCyan)
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.account_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                    )
                    Spacer(modifier = Modifier.width(17.dp))
                    Text(
                        text = stringResource(R.string.my_account),
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = EerieBlack
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .width(327.dp)
                .height(160.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color = AliceBlue)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 10.dp, horizontal = 20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .setNoRippleClickable {
                            navController.medicineNavigateSingleTop(Screens.AccountDetails.route)
                        }
                ) {
                    Text(
                        text = stringResource(R.string.my_account),
                        color = if (userDetails != null) SmokyBlack else Color.Red,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.arrow_right),
                        contentDescription = null,
                    )
                }
                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = 1.dp)
                        .fillMaxWidth()
                        .height(1.dp),
                    color = SmokyBlack
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .setNoRippleClickable {
                            navController.medicineNavigateSingleTop(Screens.ChangeLanguage.route)
                        }
                ) {
                    Text(
                        text = stringResource(R.string.change_language),
                        color = SmokyBlack,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.arrow_right),
                        contentDescription = null,
                    )
                }
                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = 1.dp)
                        .fillMaxWidth()
                        .height(1.dp),
                    color = SmokyBlack
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .setNoRippleClickable {
                            navController.medicineNavigateSingleTop(Screens.SOSContact.route)
                        }
                ) {
                    Text(
                        text = stringResource(R.string.sos_contact),
                        color = if (sosContact != null) SmokyBlack else Color.Red,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.arrow_right),
                        contentDescription = null,
                    )
                }
                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = 1.dp)
                        .fillMaxWidth()
                        .height(1.dp),
                    color = SmokyBlack
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .setNoRippleClickable {
                            dialogState = LogoutDialogState.LOGOUT
                        }
                ) {
                    Text(
                        text = stringResource(R.string.logout),
                        color = SmokyBlack,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.logout_icon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
        LogoutDialogState(
            showDialog = dialogState,
            closeDialog = {
                dialogState = LogoutDialogState.NONE
            },
            onLogout = {
                // Delete the SOS contact before logging out
                sosViewModel.deleteSOSContact {
                    homeScreenManager.reset()
                    accountViewModel.logout()
                    navController.navigate(Screens.Welcome.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
        )
    }
}