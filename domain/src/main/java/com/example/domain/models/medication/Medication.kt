package com.example.domain.models.medication

enum class FormOfMedicine {
    INHALER, PILL, SOLUTION, DROPS, INJECTION, OTHER
}

data class Medication(
    val id: String = "",
    val medication: String = "",
    val formOfMedicine: String = "",
    val condition: String = ""
)