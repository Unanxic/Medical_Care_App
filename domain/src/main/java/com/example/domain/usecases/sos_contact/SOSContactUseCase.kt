package com.example.domain.usecases.sos_contact

import com.example.domain.models.SOSContactExtended
import com.example.domain.repositories.sos_contact.SOSContactRepository


class SOSContactUseCase(private val sosContactRepository: SOSContactRepository) {

    fun getSOSContact(): SOSContactExtended? {
        return sosContactRepository.fetchSOSContact()
    }

    fun saveSOSContact(phoneNumber: String) {
        val sosContact = SOSContactExtended(phoneNumber = phoneNumber)
        sosContactRepository.saveSOSContact(sosContact)
    }

    fun deleteSOSContact() {
        sosContactRepository.deleteSOSContact()
    }
}