package com.example.domain.repositories.sos_contact

import com.example.domain.models.SOSContactExtended

interface SOSContactRepository {
    fun fetchSOSContact(): SOSContactExtended?
    fun saveSOSContact(sosContact: SOSContactExtended)
    fun deleteSOSContact()
}