package com.example.data.repositories.sos_contact

import com.example.data.database.realm_models.SOSContactRealmModel
import com.example.data.database.sources.RealmDataSource
import com.example.domain.models.SOSContactExtended
import com.example.domain.repositories.sos_contact.SOSContactRepository

class SOSContactRepositoryImpl(
    private val realmDataSource: RealmDataSource
) : SOSContactRepository {

    override fun fetchSOSContact(): SOSContactExtended? {
        return realmDataSource.fetchSOSContact()?.mapToDomainModel()
    }

    override fun saveSOSContact(sosContact: SOSContactExtended) {
        val sosContactRealm = SOSContactRealmModel().apply {
            phoneNumber = sosContact.phoneNumber.toString()
        }
        realmDataSource.saveSOSContact(sosContactRealm)
    }

    override fun deleteSOSContact() {
        realmDataSource.deleteSOSContact()
    }
}