package com.loskon.features.settings.domain

interface SettingsRepository {
    suspend fun clearData()
}