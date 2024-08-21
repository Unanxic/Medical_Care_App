package com.example.medicalcareapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.data.repositories.firebase.FirebaseRepository
import com.example.domain.models.reminder.Reminder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val MEDICATION_INTENT_ID = "medication_intent_id"
const val MEDICATION_NOTIFICATION = "medication_notification"

class MedicationAppBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("MedicationReceiver", "Broadcast received.")
        context?.let {
            val action = intent?.action
            if (action != null && action == "com.example.medicalcareapp.ACTION_REMINDER") {
                val reminderId = intent.getStringExtra(MEDICATION_INTENT_ID)
                val medicationName = intent.getStringExtra("MEDICATION_NAME")

                if (reminderId != null && medicationName != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val reminder = fetchReminderById(reminderId, medicationName)
                        reminder?.let { fetchedReminder ->
                            showNotification(context, reminderId, fetchedReminder.medicineName)
                        } ?: run {
                            Log.e("MedicationReceiver", "Reminder not found.")
                        }
                    }
                } else {
                    Log.e("MedicationReceiver", "Reminder ID or Medication Name is null.")
                }
            } else {
                Log.e("MedicationReceiver", "Received unexpected action: $action")
            }
        }
    }

    private suspend fun fetchReminderById(reminderId: String, medicationName: String): Reminder? {
        val repository = FirebaseRepository()
        return try {
            repository.getReminderById(reminderId, medicationName)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun showNotification(context: Context, reminderId: String, medicineName: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "REMINDER_CHANNEL_ID"
            val channelName = "Reminder Notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = "Channel for medication reminders"
                enableVibration(true)
                setShowBadge(true)
                setBypassDnd(true)
                lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            }
            notificationManager.createNotificationChannel(channel)
        }

        if (medicineName.isEmpty()) {
            Log.e("MedicationReceiver", "Medicine name is empty.")
            return
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(MEDICATION_INTENT_ID, reminderId) // Optionally, pass data
            putExtra("MEDICATION_NAME", medicineName)
        }

        // Create the PendingIntent
        val pendingIntent = PendingIntent.getActivity(
            context,
            reminderId.hashCode(), // Use reminderId to ensure unique PendingIntents for different reminders
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )


        val notification = NotificationCompat.Builder(context, "REMINDER_CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_dose) // Ensure this resource exists
            .setContentTitle("Medication Reminder")
            .setContentText("Time to take your medicine: $medicineName")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(reminderId.hashCode(), notification)

        Log.d("MedicationReceiver", "Notification shown for medicine: $medicineName with ID: $reminderId")
    }
}
