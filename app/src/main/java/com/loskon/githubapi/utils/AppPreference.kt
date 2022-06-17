package com.loskon.githubapi.utils

import android.content.Context
import androidx.preference.PreferenceManager

object AppPreference {

    const val PREF_KEY_DARK_MODE = "dark_mode_pref_key"

    fun setStringPreference(context: Context, key: String, value: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getStringPreference(context: Context, key: String, def: String = ""): String {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString(key, def) ?: ""
    }

    fun setIntPreference(context: Context, key: String, value: Int) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getIntPreference(context: Context, key: String, def: Int = 0): Int {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getInt(key, def)
    }

    fun setLongPreference(context: Context, key: String, value: Long) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit().putLong(key, value).apply()
    }

    fun getLongPreference(context: Context, key: String, def: Long = 0): Long {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getLong(key, def)
    }

    fun setBooleanPreference(context: Context, key: String, value: Boolean) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBooleanPreference(context: Context, key: String, def: Boolean = true): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getBoolean(key, def)
    }

    fun setDarkMode(context: Context, mode: Boolean) {
        setBooleanPreference(context, PREF_KEY_DARK_MODE, mode)
    }

    fun hasDarkMode(context: Context): Boolean {
        return getBooleanPreference(context, PREF_KEY_DARK_MODE, false)
    }
}