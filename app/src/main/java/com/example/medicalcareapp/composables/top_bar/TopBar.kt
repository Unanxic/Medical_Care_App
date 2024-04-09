package com.example.medicalcareapp.composables.top_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
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
            TopBarLayouts.MEDICAL_HISTORY_TEXT -> {
                Text(
                    text = "Medicine History",
                    fontSize = 20.sp,
                    color = EerieBlack,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
                )
            }
            TopBarLayouts.CONTACTS_TEXT -> {
                Text(
                    text = "Emergency Contacts",
                    fontSize = 20.sp,
                    color = EerieBlack,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            TopBarLayouts.ACCOUNT_TEXT -> {
                Text(
                    text = "Account Settings",
                    fontSize = 20.sp,
                    color = EerieBlack,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
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