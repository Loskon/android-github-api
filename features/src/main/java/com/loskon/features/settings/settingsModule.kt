package com.loskon.features.settings

import com.loskon.features.settings.data.SettingsRepositoryImpl
import com.loskon.features.settings.domain.SettingsInteractor
import com.loskon.features.settings.domain.SettingsRepository
import com.loskon.features.settings.presentation.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
    viewModel { SettingsViewModel(get()) }
    factory { SettingsInteractor(get()) }
}