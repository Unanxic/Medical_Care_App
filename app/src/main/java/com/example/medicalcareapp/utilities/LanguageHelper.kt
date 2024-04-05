package com.example.medicalcareapp.utilities

import android.content.Context
import com.example.domain.locale.Language
import com.example.domain.usecases.locale.LocaleUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent
import java.util.Locale

sealed class LanguageHelperEvents {
    data class LanguageChanged(val context: Context) : LanguageHelperEvents()
}

object LanguageHelper {
    private val localeUseCase = KoinJavaComponent.get<LocaleUseCase>(LocaleUseCase::class.java)

    private val _updateEvents = MutableSharedFlow<LanguageHelperEvents>()
    val updateEvents = _updateEvents.asSharedFlow()

    fun setSelectedLanguage(context: Context, languageCode: Language) {
        val locale = Locale(languageCode.code)
        Locale.setDefault(locale)

        val config = context.resources.configuration
        config.setLocale(locale)

        val updateContext = context.createConfigurationContext(config)

        CoroutineScope(Dispatchers.IO).launch {
            localeUseCase.saveSelectedLanguage(languageCode)
            _updateEvents.emit(LanguageHelperEvents.LanguageChanged(updateContext))
        }
    }

    fun getLanguage(): Language {
        return localeUseCase.getSelectedLanguage()
    }
}