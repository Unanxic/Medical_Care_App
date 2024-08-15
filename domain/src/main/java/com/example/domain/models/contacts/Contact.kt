package com.example.domain.models.contacts

data class Contact(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val phoneNumberOne: String = "",
    val phoneNumberTwo: String? = null,
    val typeOfContact: String = "",
    val doctorsSpecialty: String? = null,
    val caregiverRole: String? = null
)