package com.example.data.repositories.firebase

import com.example.domain.models.contacts.Contact
import com.example.domain.models.medication.Medication
import com.example.domain.models.reminder.Reminder
import com.example.domain.models.user_details.UserDetails
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID

class FirebaseRepository {

    private val database = Firebase.database
    private val userId: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid


    //medication
    suspend fun saveMedication(medication: Medication) {
        userId?.let {
            val myRef = database.getReference("users").child(it).child("medications")
            val key = myRef.push().key // Generate a unique key
            key?.let { id ->
                val medicationWithId = medication.copy(id = id)
                myRef.child(id).setValue(medicationWithId).await() // Save with the generated id
            }
        }
    }

    fun generateMedicationId(): String {
        return database.getReference("dummy").push().key ?: UUID.randomUUID().toString()
    }

    suspend fun getMedicationById(medicationId: String): Medication? {
        return userId?.let {
            val myRef = database.getReference("users").child(it).child("medications").child(medicationId)
            myRef.get().await().getValue(Medication::class.java)
        }
    }

    fun getMedicationsFlow(): Flow<List<Medication>> = callbackFlow {
        val myRef = database.getReference("users").child(userId!!).child("medications")
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val medications = mutableListOf<Medication>()
                snapshot.children.forEach { dataSnapshot ->
                    dataSnapshot.getValue(Medication::class.java)?.let { medication ->
                        medications.add(medication)
                    }
                }
                trySend(medications)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        myRef.addValueEventListener(listener)
        awaitClose { myRef.removeEventListener(listener) }
    }

    suspend fun deleteMedication(medicationId: String) {
        userId?.let {
            val myRef = database.getReference("users").child(it).child("medications").child(medicationId)
            myRef.removeValue().await()
        }
    }

    //contacts
    suspend fun saveContact(contact: Contact) {
        userId?.let {
            val myRef = database.getReference("users").child(it).child("contacts")
            val key = myRef.push().key ?: UUID.randomUUID().toString() // Generate a unique key
            val contactWithId = contact.copy(id = key)
            myRef.child(key).setValue(contactWithId).await() // Save with the generated id
        }
    }

    fun generateContactId(): String {
        return database.getReference("dummy").push().key ?: UUID.randomUUID().toString()
    }

    suspend fun getContactById(contactId: String): Contact? {
        return userId?.let {
            val myRef = database.getReference("users").child(it).child("contacts").child(contactId)
            myRef.get().await().getValue(Contact::class.java)
        }
    }

    fun getContactsFlow(): Flow<List<Contact>> = callbackFlow {
        val myRef = database.getReference("users").child(userId!!).child("contacts")
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val contacts = mutableListOf<Contact>()
                snapshot.children.forEach { dataSnapshot ->
                    dataSnapshot.getValue(Contact::class.java)?.let { contact ->
                        contacts.add(contact)
                    }
                }
                trySend(contacts)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        myRef.addValueEventListener(listener)
        awaitClose { myRef.removeEventListener(listener) }
    }
    suspend fun deleteContact(contactId: String) {
        userId?.let {
            val myRef = database.getReference("users").child(it).child("contacts").child(contactId)
            myRef.removeValue().await()
        }
    }

    //user
    suspend fun saveUserDetails(userDetails: UserDetails) {
        userId?.let {
            val myRef = database.getReference("users").child(it).child("accountDetails")
            myRef.setValue(userDetails).await()
        }
    }

    suspend fun getUserDetails(): UserDetails? {
        return userId?.let {
            val myRef = database.getReference("users").child(it).child("accountDetails")
            myRef.get().await().getValue(UserDetails::class.java)
        }
    }

    suspend fun deleteUserDetails() {
        userId?.let {
            val myRef = database.getReference("users").child(it).child("accountDetails")
            myRef.removeValue().await()
        }
    }

    //reminder
    // Reminder-related methods
    suspend fun saveReminder(reminder: Reminder, medicationName: String) {
        userId?.let {
            val myRef = database.getReference("users").child(it).child("reminders").child(medicationName)
            val key = generateReminderId()  // Use the new generateReminderId method
            val reminderWithId = reminder.copy(reminderId = key)
            myRef.child(key).setValue(reminderWithId).await()
        }
    }

    fun generateReminderId(): String {
        return database.getReference("dummy").push().key ?: UUID.randomUUID().toString()
    }

    fun getRemindersFlow(): Flow<List<Reminder>> = callbackFlow {
        userId?.let { userId ->
            val myRef = database.getReference("users").child(userId).child("reminders")
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val reminders = mutableListOf<Reminder>()
                    snapshot.children.forEach { medicationSnapshot ->
                        medicationSnapshot.children.forEach { reminderSnapshot ->
                            val reminder = reminderSnapshot.getValue(Reminder::class.java)
                            if (reminder != null) {
                                reminders.add(reminder)
                            } else {
                                println("Reminder is null, failed to parse snapshot.")
                            }
                        }
                    }
                    println("Fetched Reminders: ${reminders.size}")
                    trySend(reminders)
                }

                override fun onCancelled(error: DatabaseError) {
                    println("Firebase error: ${error.message}")
                    close(error.toException())
                }
            }
            myRef.addValueEventListener(listener)
            awaitClose { myRef.removeEventListener(listener) }
        } ?: close(IllegalStateException("User ID is null"))
    }

    suspend fun deleteReminder(reminderId: String, medicationName: String) {
        userId?.let {
            val myRef = database.getReference("users").child(it).child("reminders").child(medicationName).child(reminderId)
            myRef.removeValue().await()
        }
    }
}