package com.example.medicalcareapp.screens.medicine_history_screen.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repositories.firebase.FirebaseRepository
import com.example.domain.models.medication.Medication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MedicationViewModel(private val repository: FirebaseRepository) : ViewModel() {

    private val _medications = MutableStateFlow<List<Medication>>(emptyList())
    val medications: StateFlow<List<Medication>> = _medications

    private val _currentMedication = MutableStateFlow<Medication?>(null)
    val currentMedication: StateFlow<Medication?> = _currentMedication

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        // Load the medications from the Firebase repository when the ViewModel is initialized
        viewModelScope.launch {
            _isLoading.value = true
            repository.getMedicationsFlow().collect { medicationList ->
                _medications.value = medicationList
                _isLoading.value = false
            }
        }
    }

    fun setMedicationName(name: String) {
        _currentMedication.value = _currentMedication.value?.copy(medication = name)
    }

    fun setFormOfMedicine(form: String) {
        _currentMedication.value = _currentMedication.value?.copy(formOfMedicine = form)
    }

    fun setCondition(condition: String) {
        _currentMedication.value = _currentMedication.value?.copy(condition = condition)
    }

    fun saveMedication() {
        viewModelScope.launch {
            _currentMedication.value?.let { repository.saveMedication(it) }
            resetMedication()
        }
    }

    fun loadMedicationById(medicationId: String) {
        viewModelScope.launch {
            val medication = repository.getMedicationById(medicationId)
            _currentMedication.value = medication
        }
    }

    fun getMedicationById(medicationId: String): StateFlow<Medication?> {
        val medicationFlow = MutableStateFlow<Medication?>(null)
        viewModelScope.launch {
            val medication = repository.getMedicationById(medicationId)
            medicationFlow.value = medication
        }
        return medicationFlow
    }

    fun deleteMedication(medicationId: String) {
        viewModelScope.launch {
            repository.deleteMedication(medicationId)
        }
    }

    private fun resetMedication() {
        _currentMedication.value = Medication()
    }
}