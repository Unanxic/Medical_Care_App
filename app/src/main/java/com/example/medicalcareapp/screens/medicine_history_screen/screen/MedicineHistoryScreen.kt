package com.example.medicalcareapp.screens.medicine_history_screen.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicalcareapp.R
import com.example.medicalcareapp.composables.ButtonComponent
import com.example.medicalcareapp.composables.IconType
import com.example.medicalcareapp.event_manager.EventManager
import com.example.medicalcareapp.extesions.medicineNavigateSingleTop
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.navigation.Screens
import com.example.medicalcareapp.screens.medicine_history_screen.components.MedicineHistoryList
import com.example.medicalcareapp.ui.theme.HookersGreen
import com.example.medicalcareapp.ui.theme.MSUGreen
import org.koin.compose.koinInject

@Composable
fun MedicineHistoryScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    eventManager: EventManager = koinInject(),
) {
    val medicineHistoryItems = listOf(
        IconType.INHALER to "Use Inhaler",
        IconType.PILL to "Take Pill",
        IconType.SOLUTION to "Use Solution",
        IconType.DROPS to "Use Drops",
        IconType.INHALER to "Use Inhaler",
        IconType.PILL to "Take Pill",
        IconType.SOLUTION to "Use Solution",
        IconType.INHALER to "Use Inhaler",
        IconType.PILL to "Take Pill",
        IconType.SOLUTION to "Use Solution",
        IconType.INHALER to "Use Inhaler",
        IconType.PILL to "Take Pill",
        IconType.SOLUTION to "Use Solution",
        IconType.INHALER to "Use Inhaler",
        IconType.PILL to "Take Pill",
        IconType.SOLUTION to "Use Solution",
        // Add more items as needed
    )
    Box(
        Modifier
            .fillMaxSize()
            .background(color = HookersGreen)
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.looking_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
            )
//            MedicineHistoryList(
//                modifier = Modifier.padding(vertical = 16.dp),
//                items = medicineHistoryItems,
//                onItemClick = { /* Handle item click */ }
//            )
            Text(
                text = stringResource(R.string.no_medicine_history),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(70.dp))
            ButtonComponent(
                onClick = {
                    navController.medicineNavigateSingleTop(Screens.AddMedicine.route)
                },
                modifier = Modifier
                    .height(50.dp)
                    .width(215.dp),
                text = stringResource(R.string.add_medicine),
                isFilled = true,
                fontSize = 16.sp,
                cornerRadius = 20,
                fillColorChoice = MSUGreen,
                contentColorChoice = Color.White
            )
        }
//        AddMoreButton(
//            onClick = {
//                //todo
//            },
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .padding(bottom = 13.dp)
//        )
    }
}

@Composable
fun AddMoreButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.add_circle),
        contentDescription = null,
        modifier = modifier.clickable {
            onClick()
        }
    )
}