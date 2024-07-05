package com.example.medicalcareapp.navigation

sealed class Screens(
    val route: String,
) {
    object Splash : Screens("splash_screen")
    object Welcome : Screens("welcome")
    object Login : Screens("login")
    object Register : Screens("register")
    object Home : Screens("home")
    //register medicine screens
    object AddMedicine : Screens("add_medicine")
    object FormOfMedicine : Screens("form_of_medicine")
    object Condition : Screens("condition")
    object Allergy : Screens("allergy")
    object SuccessfulMedicineRegistration : Screens("successful_medicine_registration")
    object ChangeLanguage : Screens("change_language")
    object AddContacts : Screens("add_contacts")
    object SOSContact : Screens("sos_contact")

    object SuccessfulAddContacts : Screens("successful_add_contacts")
}