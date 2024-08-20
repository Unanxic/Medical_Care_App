package com.example.medicalcareapp.screens.reminder_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repositories.firebase.FirebaseRepository
import com.example.domain.models.reminder.Reminder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class ReminderViewModel(private val repository: FirebaseRepository) : ViewModel() {

    private val _reminders = MutableStateFlow<List<Reminder>>(emptyList())
    val reminders: StateFlow<List<Reminder>> = _reminders

    private val _currentReminder = MutableStateFlow(Reminder())
    val currentReminder: StateFlow<Reminder> = _currentReminder

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        // Load all reminders by default when the ViewModel is initialized
        loadAllReminders()
    }

    private fun loadAllReminders() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getRemindersFlow().collect { reminderList ->
                _reminders.value = reminderList
                _isLoading.value = false
            }
        }
    }

    fun setReminderData(medicineName: String, recurrence: String, startDate: Long, endDate: Long, timeOne: String, timeTwo: String? = null, timeThree: String? = null, timeFour: String? = null) {
        _currentReminder.value = Reminder(
            medicineName = medicineName,
            recurrence = recurrence,
            startDate = startDate,
            endDate = endDate,
            timeOne = timeOne,
            timeTwo = timeTwo,
            timeThree = timeThree,
            timeFour = timeFour
        )
    }

    fun saveReminder() {
        viewModelScope.launch {
            val reminderToSave = _currentReminder.value.copy(reminderId = repository.generateReminderId())
            repository.saveReminder(reminderToSave, reminderToSave.medicineName)
            resetReminder()
        }
    }

    private fun resetReminder() {
        _currentReminder.value = Reminder()
    }

    fun getRemindersForDate(dateInMillis: Long): List<Reminder> {
        val selectedDateCalendar = Calendar.getInstance().apply { timeInMillis = dateInMillis }
        println("Selected date: ${selectedDateCalendar.time}")

        return _reminders.value.filter { reminder ->
            val startCalendar = Calendar.getInstance().apply { timeInMillis = reminder.startDate }
            val endCalendar = Calendar.getInstance().apply {
                timeInMillis = reminder.endDate
                add(Calendar.DAY_OF_YEAR, 1) // Include the end date
            }

            val isWithinRange = (dateInMillis in reminder.startDate..endCalendar.timeInMillis)

            when (reminder.recurrence) {
                "Daily" -> {
                    isSameDay(dateInMillis, reminder.startDate) || isWithinRange
                }
                "Weekly" -> {
                    isWithinRange && isSameDayOfWeek(selectedDateCalendar, startCalendar)
                }
                "Monthly" -> {
                    isWithinRange && isSameDayOfMonth(selectedDateCalendar, startCalendar)
                }
                else -> false
            }
        }.also { filteredReminders ->
            println("Filtered reminders count: ${filteredReminders.size}")
        }
    }

    private fun isSameDayOfWeek(cal1: Calendar, cal2: Calendar): Boolean {
        return cal1.get(Calendar.DAY_OF_WEEK) == cal2.get(Calendar.DAY_OF_WEEK)
    }

    private fun isSameDayOfMonth(cal1: Calendar, cal2: Calendar): Boolean {
        return cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
    }

    private fun isSameDay(millis1: Long, millis2: Long): Boolean {
        val cal1 = Calendar.getInstance().apply { timeInMillis = millis1 }
        val cal2 = Calendar.getInstance().apply { timeInMillis = millis2 }
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }
}