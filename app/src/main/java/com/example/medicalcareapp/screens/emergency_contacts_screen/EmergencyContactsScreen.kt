package com.example.medicalcareapp.screens.emergency_contacts_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.medicalcareapp.composables.ContactIconType
import com.example.medicalcareapp.event_manager.EventManager
import com.example.medicalcareapp.extesions.makePhoneCall
import com.example.medicalcareapp.screens.emergency_contacts_screen.composables.ContactsList
import com.example.medicalcareapp.ui.theme.HookersGreen
import org.koin.compose.koinInject

@Composable
fun EmergencyContactsScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    eventManager: EventManager = koinInject(),
) {
    val emergencyContactsItems = listOf(
        Triple(ContactIconType.DOCTOR, "Dr. Joseph Brostito", "123-456-7890"),
        Triple(ContactIconType.PHARMACY, "Pharmacy ABC", "987-654-3210"),
        Triple(ContactIconType.CAREGIVER, "Caregiver John Doe", "555-123-4567")
    )
    val context = LocalContext.current

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
//            Image(
//                painter = painterResource(id = R.drawable.looking_icon),
//                contentDescription = null,
//                modifier = Modifier
//                    .size(200.dp)
//            )
            ContactsList(
                items = emergencyContactsItems,
                onPhoneNumberClick = { phoneNumber ->
                    context.makePhoneCall(phoneNumber)
                },
                onDetailsClick = {
                    //todo
                }
            )
//            Text(
//                text = stringResource(R.string.no_record),
//                color = Color.White,
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Light,
//                textAlign = TextAlign.Center
//            )
//            Spacer(modifier = Modifier.height(70.dp))
//            ButtonComponent(
//                onClick = {
//                    navController.medicineNavigateSingleTop(Screens.AddContacts.route)
//                },
//                modifier = Modifier
//                    .height(50.dp)
//                    .width(215.dp),
//                text = stringResource(R.string.add_record),
//                isFilled = true,
//                fontSize = 16.sp,
//                cornerRadius = 20,
//                fillColorChoice = MSUGreen,
//                contentColorChoice = Color.White
//            )
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