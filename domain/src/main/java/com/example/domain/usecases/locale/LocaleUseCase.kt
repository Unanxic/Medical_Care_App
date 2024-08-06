package com.example.domain.usecases.locale

import com.example.domain.locale.Language
import com.example.domain.repositories.locale.LocaleRepository

class LocaleUseCase(private val localeRepository: LocaleRepository) : LocaleRepository {
    override suspend fun saveSelectedLanguage(localeCode: Language) {
        localeRepository.saveSelectedLanguage(localeCode)
    }

    override fun getSelectedLanguage(): Language {
        return localeRepository.getSelectedLanguage()
    }
}