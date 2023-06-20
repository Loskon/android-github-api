package com.loskon.githubapi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.loskon.base.extension.activity.setTaskDescriptionColor

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onAttachedToWindow() {
        setTaskDescriptionColor(getColor(R.color.bg_task_description_color))
        super.onAttachedToWindow()
    }

    companion object {
        fun makeIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}