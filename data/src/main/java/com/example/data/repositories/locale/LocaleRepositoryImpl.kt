package com.example.data.repositories.locale

import android.content.Context
import android.content.SharedPreferences
import com.example.data.utils.toLog
import com.example.domain.locale.Language
import com.example.domain.repositories.locale.LocaleRepository

class LocaleRepositoryImpl(private val context: Context) : LocaleRepository {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    override suspend fun saveSelectedLanguage(localeCode: Language) {
        "saving: $localeCode".toLog()
        with(sharedPreferences.edit()) {
            putString("SelectedLanguage", localeCode.code)
            apply()
        }
    }

    override fun getSelectedLanguage(): Language {
        val code = sharedPreferences.getString("SelectedLanguage", Language.ENGLISH.code)
        "read: $code".toLog()
        return Language.values().firstOrNull { it.code == code } ?: Language.ENGLISH
    }
}