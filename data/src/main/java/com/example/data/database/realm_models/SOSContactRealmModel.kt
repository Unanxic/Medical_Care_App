package com.example.data.database.realm_models

import com.example.domain.models.SOSContactExtended
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId

//open class SOSContactRealmModel : RealmObject() {
//    @PrimaryKey
//    var id: String = "SOS_CONTACT"
//    var number: String? = null
//
//    fun mapToDomainModel() = SOSContactExtended(phoneNumber = number)
//}

open class SOSContactRealmModel: RealmObject() {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var phoneNumber: String = ""

    fun mapToDomainModel() = SOSContactExtended(phoneNumber = phoneNumber)
}