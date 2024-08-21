package com.example.domain.models.reminder

data class Reminder(
    val reminderId: String = "",
    val medicineName: String = "",
    val recurrence: String = "",
    val startDate: Long = 0L,
    val endDate: Long = 0L,
    val times: List<ReminderTime> = emptyList()
)

data class ReminderTime(
    val timeId: String = "",
    val time: String = ""
)