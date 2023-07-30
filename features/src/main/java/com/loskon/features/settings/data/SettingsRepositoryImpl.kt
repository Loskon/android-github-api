package com.loskon.features.settings.data

import com.loskon.database.source.LocalDataSource
import com.loskon.features.settings.domain.SettingsRepository

class SettingsRepositoryImpl(
    private val localDataSource: LocalDataSource
) : SettingsRepository {
    override suspend fun clearData() {
        localDataSource.clearData()
    }
}