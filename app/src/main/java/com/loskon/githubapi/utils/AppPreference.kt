package com.loskon.githubapi.utils

import android.content.Context
import androidx.preference.PreferenceManager
import com.loskon.githubapi.R

object AppPreference {

    fun save(context: Context, key: String, value: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun load(context: Context, key: String, def: String = ""): String {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString(key, def) ?: ""
    }

    fun save(context: Context, key: String, value: Int) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun load(context: Context, key: String, def: Int = 0): Int {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getInt(key, def)
    }

    fun save(context: Context, key: String, value: Long) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit().putLong(key, value).apply()
    }

    fun load(context: Context, key: String, def: Long = 0): Long {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getLong(key, def)
    }

    fun save(context: Context, key: String, value: Boolean) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun load(context: Context, key: String, def: Boolean = true): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getBoolean(key, def)
    }

    fun remove(context: Context, key: String) {
        val preference = PreferenceManager.getDefaultSharedPreferences(context)
        preference.edit().remove(key).apply()
    }

    fun getHasDarkMode(context: Context): Boolean {
        val key = context.getString(R.string.dark_mode_key)
        return load(context, key, false)
    }

    fun getPageSize(context: Context): Int {
        val key = context.getString(R.string.number_of_results_key)
        val def = context.resources.getInteger(R.integer.number_of_results)
        return load(context, key, def)
    }

    fun getSince(context: Context): Int {
        val key = context.getString(R.string.since_key)
        val def = context.resources.getInteger(R.integer.since)
        return load(context, key, def)
    }
}