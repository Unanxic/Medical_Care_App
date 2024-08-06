package com.example.domain.repositories.locale

import com.example.domain.locale.Language

interface LocaleRepository {
    suspend fun saveSelectedLanguage(localeCode: Language)
    fun getSelectedLanguage(): Language
}