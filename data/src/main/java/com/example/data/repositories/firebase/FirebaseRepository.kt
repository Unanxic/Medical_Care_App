package com.example.data.repositories.firebase

import com.example.domain.models.contacts.Contact
import com.example.domain.models.medication.Medication
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

}