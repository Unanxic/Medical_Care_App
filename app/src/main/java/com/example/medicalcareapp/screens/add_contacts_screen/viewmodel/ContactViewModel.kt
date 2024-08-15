package com.example.medicalcareapp.screens.add_contacts_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repositories.firebase.FirebaseRepository
import com.example.domain.models.contacts.Contact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContactViewModel(private val repository: FirebaseRepository) : ViewModel() {

    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts: StateFlow<List<Contact>> = _contacts

    private val _currentContact = MutableStateFlow(Contact())
    val currentContact: StateFlow<Contact> = _currentContact

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        // Load the contacts from the Firebase repository when the ViewModel is initialized
        viewModelScope.launch {
            _isLoading.value = true
            repository.getContactsFlow().collect { contactList ->
                _contacts.value = contactList
                _isLoading.value = false
            }
        }
    }

    fun setContactName(name: String) {
        _currentContact.value = _currentContact.value.copy(name = name)
    }

    fun setContactEmail(email: String) {
        _currentContact.value = _currentContact.value.copy(email = email)
    }

    fun setPhoneNumberOne(phoneNumberOne: String) {
        _currentContact.value = _currentContact.value.copy(phoneNumberOne = phoneNumberOne)
    }

    fun setPhoneNumberTwo(phoneNumberTwo: String?) {
        _currentContact.value = _currentContact.value.copy(phoneNumberTwo = phoneNumberTwo)
    }

    fun setTypeOfContact(type: String) {
        _currentContact.value = _currentContact.value.copy(typeOfContact = type)
    }

    fun setDoctorsSpecialty(doctorsSpecialty: String?) {
        _currentContact.value = _currentContact.value.copy(doctorsSpecialty = doctorsSpecialty)
    }

    fun setCaregiverRole(caregiverRole: String?) {
        _currentContact.value = _currentContact.value.copy(caregiverRole = caregiverRole)
    }

    fun saveContact() {
        viewModelScope.launch {
            val contactToSave = if (_currentContact.value.id.isBlank()) {
                _currentContact.value.copy(id = repository.generateContactId())
            } else {
                _currentContact.value
            }
            repository.saveContact(contactToSave)
            resetContact()
        }
    }

    fun loadContactById(contactId: String) {
        viewModelScope.launch {
            val contact = repository.getContactById(contactId)
            if (contact != null) {
                _currentContact.value = contact
            }
        }
    }

    fun deleteContact(contactId: String) {
        viewModelScope.launch {
            repository.deleteContact(contactId)
        }
    }

    private fun resetContact() {
        _currentContact.value = Contact()
    }
}