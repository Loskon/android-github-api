package com.loskon.githubapi.app.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.loskon.githubapi.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    companion object {
        fun makeIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}