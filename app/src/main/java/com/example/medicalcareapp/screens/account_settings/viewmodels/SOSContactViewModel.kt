package com.example.medicalcareapp.screens.account_settings.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.SOSContactExtended
import com.example.domain.usecases.sos_contact.SOSContactUseCase
import com.example.medicalcareapp.extesions.makePhoneCall
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SOSContactViewModel(
    private val sosContactUseCase: SOSContactUseCase
) : ViewModel() {

    private val _sosContact = MutableStateFlow<SOSContactExtended?>(null)
    val sosContact: StateFlow<SOSContactExtended?> = _sosContact

    init {
        loadSOSContact()
    }

    private fun loadSOSContact() {
        viewModelScope.launch {
            val contact = sosContactUseCase.getSOSContact()
            _sosContact.value = contact
        }
    }

    fun saveSOSContact(phoneNumber: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            sosContactUseCase.saveSOSContact(phoneNumber)
            _sosContact.value = SOSContactExtended(phoneNumber = phoneNumber)
        }.invokeOnCompletion {
            CoroutineScope(Dispatchers.Main).launch {
                onComplete()
            }
        }
    }


    fun deleteSOSContact(onComplete: () -> Unit) {
        viewModelScope.launch {
            sosContactUseCase.deleteSOSContact()
            _sosContact.value = null
        }.invokeOnCompletion {
            CoroutineScope(Dispatchers.Main).launch {
                onComplete()
            }
        }
    }

    fun makePhoneCall(context: Context) {
        _sosContact.value?.phoneNumber?.let { phoneNumber ->
            context.makePhoneCall(phoneNumber)
        }
    }
}
