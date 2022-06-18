package com.loskon.githubapi.app.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.loskon.githubapi.R
import com.loskon.githubapi.utils.AppPreference
import com.loskon.githubapi.utils.ColorUtil

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onStart() {
        ColorUtil.installColorTaskDescription(this, AppPreference.hasDarkMode(this))
        super.onStart()
    }

    companion object {
        fun makeIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}