package com.loskon.githubapi.app.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import com.loskon.githubapi.R
import com.loskon.githubapi.base.extension.activity.installColorTaskDescription

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onAttachedToWindow() {
        installColorTaskDescription(getColor(R.color.task_description))
        super.onAttachedToWindow()
    }

    companion object {
        fun makeIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}