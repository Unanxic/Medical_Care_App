package com.example.data.database.sources

import android.content.Context
import com.example.data.database.json_initialization.JsonInitializable
import com.example.data.database.realm_models.SOSContactRealmModel
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmObject
import org.json.JSONArray
import java.io.IOException

class RealmDataSourceImpl(private val context: Context) : RealmDataSource {

    override fun initializeRealm() {
        Realm.init(context)
        val realmConfig = RealmConfiguration.Builder()
            .name("medicalApp.db")
            .schemaVersion(1)
            .allowWritesOnUiThread(true)// Allow writes on UI thread
            .deleteRealmIfMigrationNeeded()// For development only. Use a migration strategy in production.
            .initialData { realm ->

            }
            .build()

        Realm.setDefaultConfiguration(realmConfig)
    }

    override fun loadInitialData(realm: Realm) {
        // Implementation for initial data load if needed
    }

    override fun <T> loadJsonData(
        realm: Realm,
        fileName: String,
        _class: Class<T>
    ) where T : RealmObject, T : JsonInitializable {
        try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val json = String(buffer, Charsets.UTF_8)
            val jsonArray = JSONArray(json)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                realm.executeTransaction {
                    val entity = it.createObject(_class, jsonObject.getLong("id"))
                    entity.initializeFromJson(jsonObject)
                }
            }
        } catch (e: IOException) {
            // Handle errors here
        }
    }

    override fun fetchSOSContact(): SOSContactRealmModel? {
        val realm = Realm.getDefaultInstance()
        return realm.use { r ->
            val result = r.where(SOSContactRealmModel::class.java).findFirst()
            result?.let { r.copyFromRealm(it) }
        }
    }

    override fun saveSOSContact(sosContact: SOSContactRealmModel) {
        val realm = Realm.getDefaultInstance()
        realm.use { r ->
            r.executeTransaction { transactionRealm ->
                val existingContact =
                    transactionRealm.where(SOSContactRealmModel::class.java).findFirst()
                if (existingContact != null) {
                    existingContact.phoneNumber = sosContact.phoneNumber
                } else {
                    transactionRealm.insertOrUpdate(sosContact)
                }
            }
        }
    }

    override fun deleteSOSContact() {
        val realm = Realm.getDefaultInstance()
        realm.use { r ->
            r.executeTransaction { transactionRealm ->
                transactionRealm.where(SOSContactRealmModel::class.java).findFirst()
                    ?.deleteFromRealm()
            }
        }
    }
}