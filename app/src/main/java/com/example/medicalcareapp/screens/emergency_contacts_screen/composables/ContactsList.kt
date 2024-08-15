package com.example.medicalcareapp.screens.emergency_contacts_screen.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.medicalcareapp.composables.EmergencyContactsCard
import com.example.medicalcareapp.extesions.ContactIconType


@Composable
fun ContactsList(
    modifier: Modifier = Modifier,
    items: List<Triple<ContactIconType, String, String>>,
    onPhoneNumberClick: (String) -> Unit,
    onDetailsClick: () -> Unit
) {
    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 17.dp),
        ) {
            item {
                Spacer(Modifier.height(18.dp))
            }
            itemsIndexed(items) { index, item ->
                val (iconType, text, phoneNumber) = item
                EmergencyContactsCard(
                    icon = iconType,
                    text = text,
                    phoneNumber = phoneNumber,
                    onPhoneNumberClick = onPhoneNumberClick,
                    onDetailsClick = onDetailsClick
                )
                if (index < items.size - 1) {
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}