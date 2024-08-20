package com.example.domain.models.reminder

data class Reminder(
    val reminderId: String = "",
    val medicineName: String = "",
    val recurrence: String = "",
    val startDate: Long = 0L,
    val endDate: Long = 0L,
    val timeOne: String = "",
    val timeTwo: String? = null,
    val timeThree: String? = null,
    val timeFour: String? = null,
    val isTaken: Boolean = false,
    val takenTime: String? = null,
    val isSkipped: Boolean = false,
)