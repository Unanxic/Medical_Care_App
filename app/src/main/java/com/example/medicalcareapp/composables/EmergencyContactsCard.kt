package com.example.medicalcareapp.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalcareapp.R
import com.example.medicalcareapp.extesions.bouncingClickable
import com.example.medicalcareapp.extesions.capitalizeWords
import com.example.medicalcareapp.ui.theme.AliceBlue
import com.example.medicalcareapp.ui.theme.CoolGrey
import com.example.medicalcareapp.ui.theme.EerieBlack
import com.example.medicalcareapp.ui.theme.MSUGreen

enum class ContactIconType(
    @DrawableRes val contactIcon: Int,
    val contactType: String
) {
    CAREGIVER(
        contactIcon = R.drawable.person_icon,
        contactType = "Caregiver"
    ),
    PHARMACY(
        contactIcon = R.drawable.pharmacy_icon,
        contactType = "Pharmacy"
    ),
    DOCTOR(
        contactIcon = R.drawable.doctor_icon,
        contactType = "Doctor"
    ),
}

@Composable
fun EmergencyContactsCard(
    icon: ContactIconType,
    text: String = "",
    phoneNumber: String = "",
    onPhoneNumberClick: (String) -> Unit,
    onDetailsClick: () -> Unit
) {
    val capitalizedText = text.capitalizeWords()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(205.dp),
        colors = CardDefaults.cardColors(
            containerColor = AliceBlue
        ),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 10.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Image(
                    painter = painterResource(id = icon.contactIcon),
                    contentDescription = "type of caregiver",
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = capitalizedText,
                        color = EerieBlack,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = icon.contactType,
                        color = EerieBlack,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(
                thickness = 1.dp,
                color = EerieBlack
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.phone_icon),
                    contentDescription = "phone",
                    modifier = Modifier.size(17.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = phoneNumber,
                    color = CoolGrey,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.bouncingClickable { onPhoneNumberClick(phoneNumber) },
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            ButtonComponent(
                onClick = onDetailsClick,
                modifier = Modifier
                    .height(39.dp),
                text = stringResource(R.string.details),
                isFilled = true,
                fontSize = 14.sp,
                cornerRadius = 40,
                fillColorChoice = MSUGreen,
                contentColorChoice = Color.White
            )
        }
    }
}