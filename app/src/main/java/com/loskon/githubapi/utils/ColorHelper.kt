package com.loskon.githubapi.utils

import androidx.appcompat.app.AppCompatDelegate

object ColorHelper {

    fun toggleDarkTheme(hasDarkMode: Boolean) {
        if (hasDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    fun installDarkTheme(hasDarkMode: Boolean) {
        if (hasDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}