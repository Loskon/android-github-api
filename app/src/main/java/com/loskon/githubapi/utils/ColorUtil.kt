package com.loskon.githubapi.utils

import android.app.Activity
import android.app.ActivityManager.TaskDescription
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.loskon.githubapi.R
import com.loskon.githubapi.base.extension.content.getColorCtx

object ColorUtil {

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

    @Suppress("DEPRECATION")
    fun installColorTaskDescription(activity: Activity, hasDarkMode: Boolean) {
        val color = if (hasDarkMode) activity.getColorCtx(R.color.github) else Color.WHITE

        val description = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            TaskDescription(null, 0, color)
        } else {
            TaskDescription(null, null, color)
        }

        activity.setTaskDescription(description)
    }
}