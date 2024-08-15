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
import androidx.compose.runtime.getValue
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
import com.example.medicalcareapp.extesions.isEmailValid
import com.example.medicalcareapp.extesions.medicineNavigateSingleTop
import com.example.medicalcareapp.navigation.Screens
import com.example.medicalcareapp.screens.add_contacts_screen.composables.ExpandableTextInputField
import com.example.medicalcareapp.screens.add_contacts_screen.composables.GreenRoundedTopBar
import com.example.medicalcareapp.screens.add_contacts_screen.composables.TextInputField
import com.example.medicalcareapp.screens.add_contacts_screen.viewmodel.ContactViewModel
import com.example.medicalcareapp.ui.theme.DesaturatedCyan
import com.example.medicalcareapp.ui.theme.LightBlue
import com.example.medicalcareapp.ui.theme.SmokyBlack
import org.koin.compose.koinInject

@Composable
fun AddContactsScreen(
    navController: NavController,
    viewModel: ContactViewModel = koinInject()
) {
    var isNavigationInProgress by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumberOne by remember { mutableStateOf("") }
    var phoneNumberTwo by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("") }
    var doctorsSpecialty by remember { mutableStateOf("") }
    var caregiverRole by remember { mutableStateOf("") }

    var isFieldExpanded by remember { mutableStateOf(false) }
    var isTypeSelectedValid by remember { mutableStateOf(false) }

    var isEmailError by rememberSaveable { mutableStateOf(false) }
    var isPhoneNumberOneError by rememberSaveable { mutableStateOf(false) }
    var isPhoneNumberTwoError by rememberSaveable { mutableStateOf(false) }

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
                hint = stringResource(R.string.ex_john_doe),
                text = name,
                onTextChanged = { name = it },
            )
            TextInputField(
                label = stringResource(R.string.email_uppercase),
                hint = stringResource(R.string.ex_example_gmail_com),
                text = email,
                onTextChanged = {
                    email = it
                    isEmailError = !it.isEmailValid()
                },
                errorMessage = stringResource(R.string.invalid_email_address),
                isErrorTextField = isEmailError,
            )
            TextInputField(
                label = stringResource(R.string.phone_number),
                hint = stringResource(R.string.ex_69),
                text = phoneNumberOne,
                onTextChanged = {
                    phoneNumberOne = it
                    isPhoneNumberOneError = it.length != 10
                },
                errorMessage = stringResource(R.string.phone_number_must_be_10_digits_long),
                isErrorTextField = isPhoneNumberOneError,
                imeAction = ImeAction.Done
            )
            ExpandableTextInputField(
                label = stringResource(R.string.phone_number_2),
                hint = stringResource(R.string.ex_2651),
                text = phoneNumberTwo,
                onTextChanged = {
                    phoneNumberTwo = it
                    isPhoneNumberTwoError = it.length != 10
                },
                isErrorTextField = isPhoneNumberTwoError,
                errorMessage = stringResource(R.string.phone_number_must_be_10_digits_long),
                imeAction = ImeAction.Done,
                expanded = isFieldExpanded,
                onExpandChange = { isFieldExpanded = it },
            )
            Text(
                text = stringResource(R.string.select_the_type_of_the_contact),
                color = SmokyBlack,
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )
            TypeOfContactSection(
                onTypeChange = { isValid, type, specialty, role ->
                    isTypeSelectedValid = isValid
                    selectedType = type
                    doctorsSpecialty = specialty ?: ""
                    caregiverRole = role ?: ""
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
            ButtonComponent(
                onClick = {
                    viewModel.setContactName(name)
                    viewModel.setContactEmail(email)
                    viewModel.setPhoneNumberOne(phoneNumberOne)
                    viewModel.setPhoneNumberTwo(phoneNumberTwo.takeIf { it.isNotBlank() })
                    viewModel.setTypeOfContact(selectedType)
                    viewModel.setDoctorsSpecialty(doctorsSpecialty.takeIf { it.isNotBlank() })
                    viewModel.setCaregiverRole(caregiverRole.takeIf { it.isNotBlank() })
                    viewModel.saveContact()
                    navController.medicineNavigateSingleTop(Screens.SuccessfulAddContacts.route)
                },
                modifier = Modifier
                    .height(50.dp)
                    .width(250.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(R.string.add_contact),
                isFilled = true,
                fontSize = 20.sp,
                cornerRadius = 20,
                fillColorChoice = LightBlue,
                contentColorChoice = SmokyBlack,
                isDisabled = email.isBlank() ||
                        name.isBlank() ||
                        phoneNumberOne.isBlank() ||
                        (isFieldExpanded && phoneNumberTwo.isBlank() || isPhoneNumberTwoError) ||
                        !isTypeSelectedValid ||
                        isEmailError ||
                        isPhoneNumberOneError
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun TypeOfContactSection(
    onTypeChange: (Boolean, String, String?, String?) -> Unit
) {
    var dropDownValue by rememberSaveable { mutableStateOf("") }

    var firstButtonSelected by rememberSaveable { mutableStateOf(false) }
    var secondButtonSelected by rememberSaveable { mutableStateOf(false) }
    var thirdButtonSelected by rememberSaveable { mutableStateOf(false) }

    val doctorString = stringResource(R.string.doctor)
    val pharmacyString = stringResource(R.string.pharmacy)
    val caregiverString = stringResource(R.string.caregiver)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        GenericButtonSwitch(
            text = doctorString,
            isSelected = firstButtonSelected
        ) {
            firstButtonSelected = !firstButtonSelected
            secondButtonSelected = false
            thirdButtonSelected = false
            onTypeChange(firstButtonSelected && dropDownValue.isNotBlank(), doctorString, dropDownValue, null)
        }
        GenericButtonSwitch(
            text = pharmacyString,
            isSelected = secondButtonSelected
        ) {
            secondButtonSelected = !secondButtonSelected
            firstButtonSelected = false
            thirdButtonSelected = false
            onTypeChange(secondButtonSelected, pharmacyString, null, null)
        }
        GenericButtonSwitch(
            text = caregiverString,
            isSelected = thirdButtonSelected
        ) {
            thirdButtonSelected = !thirdButtonSelected
            firstButtonSelected = false
            secondButtonSelected = false
            onTypeChange(thirdButtonSelected && dropDownValue.isNotBlank(), caregiverString, null, dropDownValue)
        }
    }

    when {
        firstButtonSelected -> {
            TextInputField(
                label = stringResource(R.string.doctors_specialty),
                hint = stringResource(R.string.ex_dentist),
                text = dropDownValue,
                onTextChanged = {
                    dropDownValue = it
                    onTypeChange(dropDownValue.isNotBlank(), doctorString, dropDownValue, null)
                }
            )
        }
        thirdButtonSelected -> {
            TextInputField(
                label = stringResource(R.string.caregiver_role),
                hint = stringResource(R.string.ex_parent),
                text = dropDownValue,
                onTextChanged = {
                    dropDownValue = it
                    onTypeChange(dropDownValue.isNotBlank(), caregiverString, null, dropDownValue)
                }
            )
        }
    }
}