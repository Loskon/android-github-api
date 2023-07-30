package com.loskon.features.settings.presentation

import com.loskon.base.presentation.viewmodel.BaseViewModel
import com.loskon.features.settings.domain.SettingsInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel(
    private val settingsInteractor: SettingsInteractor
) : BaseViewModel() {

    private val settingsAction = MutableStateFlow<SettingsAction?>(null)
    val getSettingsAction get() = settingsAction.asStateFlow()

    fun clearData() {
        launchErrorJob {
            settingsInteractor.clearData()
            settingsAction.emit(SettingsAction.ShowSnackbar)
        }
    }
}