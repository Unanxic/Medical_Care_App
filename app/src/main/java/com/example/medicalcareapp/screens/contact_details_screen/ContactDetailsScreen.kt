package com.example.medicalcareapp.screens.contact_details_screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.domain.models.contacts.Contact
import com.example.medicalcareapp.R
import com.example.medicalcareapp.composables.ButtonComponent
import com.example.medicalcareapp.composables.alert_dialogs.DeleteDialogState
import com.example.medicalcareapp.extesions.CARD_ELEVATION
import com.example.medicalcareapp.extesions.ContactIconType
import com.example.medicalcareapp.extesions.getContactIconType
import com.example.medicalcareapp.extesions.makePhoneCall
import com.example.medicalcareapp.extesions.sendEmail
import com.example.medicalcareapp.extesions.setNoRippleClickable
import com.example.medicalcareapp.screens.add_contacts_screen.viewmodel.ContactViewModel
import com.example.medicalcareapp.ui.theme.AliceBlue
import com.example.medicalcareapp.ui.theme.EerieBlack
import com.example.medicalcareapp.ui.theme.HookersGreen
import com.example.medicalcareapp.ui.theme.JetStream
import com.example.medicalcareapp.ui.theme.MSUGreen
import com.example.medicalcareapp.ui.theme.SmokyBlack
import org.koin.compose.koinInject


@Composable
fun ContactDetailsScreen(
    navController: NavController,
    contactId: String,
    viewModel: ContactViewModel = koinInject(),
) {

    LaunchedEffect(contactId) {
        viewModel.loadContactById(contactId)
    }

    val contact by viewModel.currentContact.collectAsState()
    val context = LocalContext.current
    var isNavigationInProgress by remember { mutableStateOf(false) }
    var deleteDialogState by remember { mutableStateOf(DeleteDialogState.NONE) }

    Box(
        Modifier
            .fillMaxSize()
            .background(color = HookersGreen),
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .size(25.dp)
                .setNoRippleClickable {
                    if (!isNavigationInProgress) {
                        isNavigationInProgress = true
                        navController.popBackStack()
                    }
                },
            tint = EerieBlack
        )
        contact.let { cont ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 45.dp)
            ) {
                val iconType = getContactIconType(cont.typeOfContact)
                ImageFunction(iconType)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = cont.name,
                    color = EerieBlack,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                Spacer(modifier = Modifier.height(10.dp))
                ContactDetailsCard(contact = cont, context = context)
                Spacer(modifier = Modifier.height(60.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ButtonComponent(
                        onClick = {
                            deleteDialogState = DeleteDialogState.DELETE_CONTACT
                        },
                        modifier = Modifier
                            .height(50.dp)
                            .width(150.dp)
                            .shadow(
                                elevation = 4.dp,
                                shape = CircleShape
                            ),
                        text = stringResource(R.string.delete),
                        isFilled = true,
                        fontSize = 20.sp,
                        cornerRadius = 20,
                        fillColorChoice = JetStream,
                        contentColorChoice = SmokyBlack
                    )
                }
            }
        }
        Image(
            painter = painterResource(id = R.drawable.waves),
            contentDescription = "Waves",
            contentScale = ContentScale.FillWidth,
            colorFilter = ColorFilter.tint(MSUGreen),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
    DeleteDialogState(
        showDialog = deleteDialogState,
        closeDialog = { deleteDialogState = DeleteDialogState.NONE },
        onDelete = {
            viewModel.deleteContact(contactId)
            navController.popBackStack()
        }
    )
}

@Composable
fun ContactDetailsCard(contact: Contact, context: Context) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = AliceBlue
        ),
        shape = RoundedCornerShape(40.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = CARD_ELEVATION)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (contact.email.isNotBlank()) {
                DetailRow(
                    label = stringResource(R.string.email_uppercase),
                    value = contact.email,
                    onClick = { context.sendEmail(contact.email) })
            }

            if (contact.phoneNumberTwo != null) {
                DetailRow(
                    label = stringResource(R.string.phone_number_1),
                    value = contact.phoneNumberOne,
                    onClick = { context.makePhoneCall(contact.phoneNumberOne) }
                )
                DetailRow(
                    label = stringResource(R.string.phone_number_two),
                    value = contact.phoneNumberTwo!!,
                    onClick = { context.makePhoneCall(contact.phoneNumberTwo!!) }
                )
            } else {
                DetailRow(
                    label = stringResource(R.string.phone_number),
                    value = contact.phoneNumberOne,
                    onClick = { context.makePhoneCall(contact.phoneNumberOne) }
                )
            }

            contact.doctorsSpecialty?.let { specialty ->
                DetailRow(label = stringResource(R.string.doctors_specialty), value = specialty)
            }

            contact.caregiverRole?.let { role ->
                DetailRow(label = stringResource(R.string.caregiver_role), value = role)
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String, onClick: (() -> Unit)? = null) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().clickable(enabled = onClick != null) { onClick?.invoke() }
    ) {
        Text(
            text = label,
            color = EerieBlack,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Start
        )
        Text(
            text = value,
            color = EerieBlack,
            fontSize = 14.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun ImageFunction(icon: ContactIconType) {
    Image(
        painter = painterResource(id = icon.contactIcon),
        contentDescription = "contact icon",
        modifier = Modifier.size(50.dp)
    )
}