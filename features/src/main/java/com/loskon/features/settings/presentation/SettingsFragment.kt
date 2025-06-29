package com.loskon.features.settings.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.SwitchPreferenceCompat
import com.loskon.base.extension.coroutines.observe
import com.loskon.base.extension.view.setDebouncePreferenceClickListener
import com.loskon.base.extension.view.setPreferenceChangeListener
import com.loskon.base.presentation.preferencefragment.BasePreferenceFragment
import com.loskon.base.utils.ColorUtil
import com.loskon.features.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BasePreferenceFragment(R.xml.root_preferences) {

    private val viewModel by viewModel<SettingsViewModel>()

    private var darkModeSwitch: SwitchPreferenceCompat? = null
    private var clearData: Preference? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        installObservers()
    }

    private fun installObservers() {
        viewModel.getSettingsAction.observe(viewLifecycleOwner) {
            when (it) {
                is SettingsAction.ShowSnackbar -> {
                    showSnackbar(getString(R.string.data_cleared), success = true)
                }
                else -> {}
            }
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?) {
        findPreferences()
        setupPreferencesListeners()

        setOnNavigateClick { findNavController().popBackStack() }
    }

    private fun findPreferences() {
        darkModeSwitch = findPreference(R.string.dark_mode_key)
        clearData = findPreference(R.string.clear_data_key)
    }

    private fun setupPreferencesListeners() {
        darkModeSwitch?.setPreferenceChangeListener {
            ColorUtil.toggleDarkMode(it)
        }
        clearData?.setDebouncePreferenceClickListener {
            viewModel.clearData()
        }
    }
}