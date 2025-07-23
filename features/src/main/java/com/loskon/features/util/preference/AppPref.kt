package com.loskon.features.util.preference

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.loskon.features.R

object AppPref {

    fun set(context: Context, key: String, value: Boolean) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit { putBoolean(key, value) }
    }

    fun get(context: Context, key: String, def: Boolean): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getBoolean(key, def)
    }

    fun remove(context: Context, key: String) {
        val preference = PreferenceManager.getDefaultSharedPreferences(context)
        preference.edit { remove(key) }
    }

    fun hasDarkMode(context: Context): Boolean {
        val key = context.getString(R.string.dark_mode_key)
        return get(context, key, false)
    }
}