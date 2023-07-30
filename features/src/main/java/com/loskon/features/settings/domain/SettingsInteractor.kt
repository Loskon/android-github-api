package com.loskon.features.settings.domain

class SettingsInteractor(
    private val settingsRepository: SettingsRepository
) {

    suspend fun clearData() {
        settingsRepository.clearData()
    }
}