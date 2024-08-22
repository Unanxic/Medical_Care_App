package com.example.medicalcareapp.screens.account_settings.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repositories.firebase.FirebaseRepository
import com.example.domain.models.sos_contact.SOSContact
import com.example.medicalcareapp.extesions.makePhoneCall
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SOSContactViewModel(
    private val repository: FirebaseRepository
) : ViewModel() {

    private val _sosContact = MutableStateFlow<SOSContact?>(null)
    val sosContact: StateFlow<SOSContact?> = _sosContact

    init {
        loadSOSContact()
    }

    private fun loadSOSContact() {
        viewModelScope.launch {
            val contact = repository.loadSOSContact()
            _sosContact.value = contact
        }
    }

    fun saveSOSContact(phoneNumber: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            val sosContact = SOSContact(phoneNumber = phoneNumber)
            repository.saveSOSContact(sosContact)
            _sosContact.value = sosContact
            onComplete()
        }
    }

    fun deleteSOSContact(onComplete: () -> Unit) {
        viewModelScope.launch {
            repository.deleteSOSContact()
            _sosContact.value = null
            onComplete()
        }
    }

    fun makePhoneCall(context: Context) {
        _sosContact.value?.phoneNumber?.let { phoneNumber ->
            context.makePhoneCall(phoneNumber)
        }
    }
}
