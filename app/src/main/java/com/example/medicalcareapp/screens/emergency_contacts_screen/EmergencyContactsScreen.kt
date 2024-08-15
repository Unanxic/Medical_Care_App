package com.example.medicalcareapp.screens.emergency_contacts_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicalcareapp.R
import com.example.medicalcareapp.composables.ButtonComponent
import com.example.medicalcareapp.composables.EmergencyContactsCard
import com.example.medicalcareapp.event_manager.EventManager
import com.example.medicalcareapp.extesions.bouncingClickable
import com.example.medicalcareapp.extesions.getContactIconType
import com.example.medicalcareapp.extesions.makePhoneCall
import com.example.medicalcareapp.extesions.medicineNavigateSingleTop
import com.example.medicalcareapp.navigation.Screens
import com.example.medicalcareapp.screens.add_contacts_screen.viewmodel.ContactViewModel
import com.example.medicalcareapp.ui.theme.HookersGreen
import com.example.medicalcareapp.ui.theme.MSUGreen
import org.koin.compose.koinInject

@Composable
fun EmergencyContactsScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    eventManager: EventManager = koinInject(),
    viewModel: ContactViewModel = koinInject(),
) {
    val context = LocalContext.current

    val contacts by viewModel.contacts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
            .background(color = HookersGreen)
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(60.dp)
                )
            }
            contacts.isEmpty() -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.looking_icon),
                        contentDescription = null,
                        modifier = Modifier.size(200.dp)
                    )
                    Text(
                        text = stringResource(R.string.no_record),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(70.dp))
                    ButtonComponent(
                        onClick = {
                            navController.medicineNavigateSingleTop(Screens.AddContacts.route)
                        },
                        modifier = Modifier
                            .height(50.dp)
                            .width(215.dp),
                        text = stringResource(R.string.add_record),
                        isFilled = true,
                        fontSize = 16.sp,
                        cornerRadius = 20,
                        fillColorChoice = MSUGreen,
                        contentColorChoice = Color.White
                    )
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(bottom = 70.dp, top = 16.dp)
                ) {
                    items(contacts) { contact ->
                        val iconType = getContactIconType(contact.typeOfContact)

                        EmergencyContactsCard(
                            icon = iconType,
                            text = contact.name,
                            phoneNumber = contact.phoneNumberOne,
                            onPhoneNumberClick = { phoneNumber ->
                                context.makePhoneCall(phoneNumber)
                            },
                            onDetailsClick = {
                                // Handle details click, maybe navigate to details screen
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                AddMoreButton(
                    onClick = {
                        navController.medicineNavigateSingleTop(Screens.AddContacts.route)
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 13.dp)
                )
            }
        }
    }
}
@Composable
private fun AddMoreButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.add_circle),
        contentDescription = null,
        modifier = modifier.bouncingClickable {
            onClick()
        }
    )
}