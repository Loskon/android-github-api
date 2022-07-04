package com.loskon.githubapi.app.presentation.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.loskon.githubapi.R
import com.loskon.githubapi.app.base.extension.activity.installTaskDescriptionColor

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onAttachedToWindow() {
        installTaskDescriptionColor(getColor(R.color.task_description_color))
        super.onAttachedToWindow()
    }

    companion object {
        fun makeIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}