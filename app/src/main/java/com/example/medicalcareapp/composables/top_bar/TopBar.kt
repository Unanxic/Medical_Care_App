package com.example.medicalcareapp.composables.top_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicalcareapp.R
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.ui.theme.DesaturatedCyan
import com.example.medicalcareapp.ui.theme.EerieBlack

@Composable
fun TopBar(
    topBarLayout: TopBarLayouts,
    header: String = "",
    navController: NavController,
    leftActionOnClick: (() -> Unit)? = null
) {
    val height = 50.dp
    Box(
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
            .background(DesaturatedCyan),
    ){
        when (topBarLayout) {
            TopBarLayouts.CALENDAR_TEXT -> {
                Text(
                    text = stringResource(R.string.calendar_lower_case),
                    fontSize = 20.sp,
                    color = EerieBlack,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            TopBarLayouts.MEDICAL_HISTORY_TEXT -> {
                Text(
                    text = stringResource(R.string.medicine_history),
                    fontSize = 20.sp,
                    color = EerieBlack,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
                )
            }
            TopBarLayouts.CONTACTS_TEXT -> {
                Text(
                    text = stringResource(R.string.emergency_contacts),
                    fontSize = 20.sp,
                    color = EerieBlack,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            TopBarLayouts.ACCOUNT_TEXT -> {
                Text(
                    text = stringResource(R.string.account_settings),
                    fontSize = 20.sp,
                    color = EerieBlack,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(25.dp)
                        .setNoRippleClickable {
                            if (leftActionOnClick != null) {
                                leftActionOnClick()
                            } else {
                                navController.popBackStack()
                            }
                        },
                    tint = EerieBlack
                )
            }
        }
    }
}