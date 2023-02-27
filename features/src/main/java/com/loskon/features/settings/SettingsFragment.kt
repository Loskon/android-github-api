package com.loskon.features.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.loskon.base.viewbinding.viewBinding
import com.loskon.features.R
import com.loskon.features.databinding.FragmentSettingsBinding
import com.loskon.base.utils.ColorUtil

class OpenSettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding by viewBinding(FragmentSettingsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.commit { replace(binding.fragmentContainerSettings.id, SettingsFragment()) }
        setupViewsListener()
    }

    private fun setupViewsListener() {
        binding.bottomBarSettings.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDivider(null)
        setDividerHeight(0)
        listView.isVerticalScrollBarEnabled = false
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        installPreferencesListener()
    }

    private fun installPreferencesListener() {
        val darkModeSwitch: SwitchPreferenceCompat? = findPreference(getString(R.string.dark_mode_key))
        darkModeSwitch?.setOnPreferenceChangeListener { _, newValue ->
            ColorUtil.toggleDarkMode(newValue as Boolean)
            true
        }
    }
}