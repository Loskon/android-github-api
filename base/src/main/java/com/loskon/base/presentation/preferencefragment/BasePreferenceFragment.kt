package com.loskon.base.presentation.preferencefragment

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.annotation.XmlRes
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.loskon.base.databinding.FragmentSettingsBinding
import com.loskon.base.viewbinding.viewBinding
import com.loskon.base.widget.snackbar.WarningSnackbar

/**
 * Custom Preference Fragment with bottom bar
 */
abstract class BasePreferenceFragment(
    @XmlRes val preferencesResId: Int
) : PreferenceFragmentCompat() {

    private val binding by viewBinding(FragmentSettingsBinding::bind)
    private var onNavigateClick: (() -> Unit)? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(preferencesResId, rootKey)
        onCreatePreferences(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView.isVerticalScrollBarEnabled = false
        binding.bottomBarSettings.setNavigationOnClickListener { onNavigateClick?.invoke() }
    }
    fun showSnackbar(message: String, success: Boolean) {
        WarningSnackbar().make(binding.root, binding.bottomBarSettings, message, success).show()
    }

    fun setOnNavigateClick(onNavigateClick: () -> Unit) {
        this.onNavigateClick = onNavigateClick
    }

    fun <T : Preference> findPreference(@StringRes preferenceId: Int): T? {
        return findPreference(getString(preferenceId))
    }

    abstract fun onCreatePreferences(savedInstanceState: Bundle?)
}