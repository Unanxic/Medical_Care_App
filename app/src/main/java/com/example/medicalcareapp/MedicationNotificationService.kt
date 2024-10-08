package com.example.medicalcareapp

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.domain.models.reminder.Reminder
import com.example.domain.models.reminder.ReminderTime
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MedicationNotificationService(
    private val context: Context
) {

    @SuppressLint("ScheduleExactAlarm")
    fun scheduleNotification(reminder: Reminder, reminderTime: ReminderTime, dateInMillis: Long) {
        // Create the Intent for the BroadcastReceiver
        val intent = Intent(context, MedicationAppBroadcastReceiver::class.java).apply {
            action = "com.example.medicalcareapp.ACTION_REMINDER"
            putExtra(MEDICATION_INTENT_ID, reminder.reminderId)
            putExtra("MEDICATION_NAME", reminder.medicineName)
            putExtra("REMINDER_TIME_ID", reminderTime.timeId)
            putExtra("REMINDER_DATE", dateInMillis)
        }

        // Create the PendingIntent to trigger the BroadcastReceiver
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            (reminder.reminderId.hashCode() + dateInMillis.hashCode() + reminderTime.time.hashCode()),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Convert the reminder's time and date to milliseconds
        val timeInMillis = convertTimeToMillis(reminderTime.time, dateInMillis)

        // Get the AlarmManager service
        val alarmService = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmService.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            dateInMillis, // Pass the exact date and time for the reminder
            pendingIntent
        )

        if (timeInMillis != null) {
            try {
                Log.d("MedicationNotification", "Setting alarm for time: $timeInMillis")
                // Set an exact alarm that can wake the device even if it's idle
                alarmService.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
            } catch (exception: SecurityException) {
                exception.printStackTrace()
            }
        } else {
            Log.e("MedicationNotification", "Failed to convert time to millis.")
        }
    }

    @Suppress("DEPRECATION")
    private fun convertTimeToMillis(time: String, date: Long): Long? {
        return try {
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            val timeDate = sdf.parse(time)
            timeDate?.let {
                val calendar = Calendar.getInstance().apply {
                    timeInMillis = date
                    set(Calendar.HOUR_OF_DAY, it.hours)
                    set(Calendar.MINUTE, it.minutes)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                Log.d("MedicationNotification", "Scheduled time in millis: ${calendar.timeInMillis}")
                calendar.timeInMillis
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}