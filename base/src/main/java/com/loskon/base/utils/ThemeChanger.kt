package com.loskon.base.utils

import androidx.appcompat.app.AppCompatDelegate

object ThemeChanger {

    fun activateDarkMode(darkMode: Boolean) {
        if (darkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}