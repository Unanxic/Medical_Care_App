package com.example.medicalcareapp.navigation.graphs

sealed class NavigationGraphs(val navRoute: String) {
    object RegisterMedicineGraph: NavigationGraphs("register_medicine_graph")
    object AddContactsGraph: NavigationGraphs("add_contacts_graph")
}
