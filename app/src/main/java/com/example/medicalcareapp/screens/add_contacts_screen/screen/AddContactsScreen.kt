package com.example.medicalcareapp.screens.add_contacts_screen.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicalcareapp.R
import com.example.medicalcareapp.composables.ButtonComponent
import com.example.medicalcareapp.composables.GenericButtonSwitch
import com.example.medicalcareapp.screens.add_contacts_screen.composables.ExpandableTextInputField
import com.example.medicalcareapp.screens.add_contacts_screen.composables.GreenRoundedTopBar
import com.example.medicalcareapp.screens.add_contacts_screen.composables.TextInputField
import com.example.medicalcareapp.ui.theme.DesaturatedCyan
import com.example.medicalcareapp.ui.theme.LightBlue
import com.example.medicalcareapp.ui.theme.SmokyBlack

@Composable
fun AddContactsScreen(
    navController: NavController,
) {
    var isNavigationInProgress by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumberOne by remember { mutableStateOf("") }
    var phoneNumberTwo by remember { mutableStateOf("") }

    var selectedIndex by remember { mutableIntStateOf(-1) }

    Column(
        Modifier
            .fillMaxSize()
            .background(color = DesaturatedCyan),
    ) {
        GreenRoundedTopBar(onBackClick = {
            if (!isNavigationInProgress) {
                isNavigationInProgress = true
                navController.popBackStack()
            }
        })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TextInputField(
                label = stringResource(R.string.name),
                hint = "ex: John Doe",
                text = name,
                onTextChanged = {name = it}
            )
            TextInputField(
                label = stringResource(R.string.email_uppercase),
                hint = "ex: example@gmail.com",
                text = email,
                onTextChanged = {email = it}
            )
            TextInputField(
                label = stringResource(R.string.phone_number),
                hint = "ex: +69...",
                text = phoneNumberOne,
                onTextChanged = {phoneNumberOne = it},
                imeAction = ImeAction.Done
            )
            ExpandableTextInputField(
                label = stringResource(R.string.phone_number_2),
                hint = "ex: +2651...",
                text = phoneNumberTwo,
                onTextChanged = { phoneNumberTwo = it },
                imeAction = ImeAction.Done
            )
            Text(
                text = stringResource(R.string.select_the_type_of_the_contact),
                color = SmokyBlack,
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )
            TypeOfContactSection()
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(12.dp))
            ButtonComponent(
                onClick = {
                    //todo
                },
                modifier = Modifier
                    .height(50.dp)
                    .width(250.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Add contact",
                isFilled = true,
                fontSize = 20.sp,
                cornerRadius = 20,
                fillColorChoice = LightBlue,
                contentColorChoice = SmokyBlack
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun TypeOfContactSection(
    ) {
    var dropDownValue by rememberSaveable { mutableStateOf("") }

    var firstButtonSelected by rememberSaveable { mutableStateOf(false) }
    var secondButtonSelected by rememberSaveable { mutableStateOf(false) }
    var thirdButtonSelected by rememberSaveable { mutableStateOf(false) }

    val isButtonSelected by remember {
        derivedStateOf {
            firstButtonSelected || secondButtonSelected || thirdButtonSelected
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        GenericButtonSwitch(
            text = stringResource(R.string.doctor),
            isSelected = firstButtonSelected
        ) {
            firstButtonSelected = !firstButtonSelected
            secondButtonSelected = false
            thirdButtonSelected = false
        }
        GenericButtonSwitch(
            text = stringResource(R.string.pharmacy),
            isSelected = secondButtonSelected
        ) {
            secondButtonSelected = !secondButtonSelected
            firstButtonSelected = false
            thirdButtonSelected = false
        }
        GenericButtonSwitch(
            text = stringResource(R.string.caregiver),
            isSelected = thirdButtonSelected
        ) {
            thirdButtonSelected = !thirdButtonSelected
            firstButtonSelected = false
            secondButtonSelected = false
        }
    }
}