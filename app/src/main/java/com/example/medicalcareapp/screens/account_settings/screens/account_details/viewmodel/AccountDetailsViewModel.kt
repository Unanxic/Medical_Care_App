package com.example.medicalcareapp.screens.account_settings.screens.account_details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repositories.firebase.FirebaseRepository
import com.example.domain.models.user_details.UserDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccountDetailsViewModel(private val repository: FirebaseRepository) : ViewModel() {

    private val _userDetails = MutableStateFlow<UserDetails?>(null)
    val userDetails: StateFlow<UserDetails?> = _userDetails

    private val _isEditing = MutableStateFlow(false)
    val isEditing: StateFlow<Boolean> = _isEditing

    init {
        loadUserDetails()
    }

    private fun loadUserDetails() {
        viewModelScope.launch {
            val details = repository.getUserDetails()
            _userDetails.value = details
        }
    }

    fun saveUserDetails(firstName: String, lastName: String, phoneNumber: String) {
        val userDetails = UserDetails(firstName, lastName, phoneNumber)
        viewModelScope.launch {
            repository.saveUserDetails(userDetails)
            _isEditing.value = false
            _userDetails.value = userDetails
        }
    }

    fun deleteUserDetails() {
        viewModelScope.launch {
            repository.deleteUserDetails()
            _isEditing.value = true
            _userDetails.value = null
        }
    }

    fun setEditing(editing: Boolean) {
        _isEditing.value = editing
    }
}