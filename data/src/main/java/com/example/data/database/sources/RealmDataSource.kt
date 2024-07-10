package com.example.data.database.sources

import com.example.data.database.json_initialization.JsonInitializable
import com.example.data.database.realm_models.SOSContactRealmModel
import io.realm.Realm
import io.realm.RealmObject

interface RealmDataSource {
    fun initializeRealm()
    fun loadInitialData(realm: Realm)
    fun <T> loadJsonData(realm: Realm, fileName: String, _class: Class<T>) where T : RealmObject, T: JsonInitializable

    fun fetchSOSContact(): SOSContactRealmModel?
    fun saveSOSContact(sosContact: SOSContactRealmModel)
    fun deleteSOSContact()
}