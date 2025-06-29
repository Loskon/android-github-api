package com.loskon.githubapi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.ktx.Firebase
import com.loskon.base.extension.activity.setTaskDescriptionColor

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAnalytics = Firebase.analytics

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN) {
            param(FirebaseAnalytics.Param.ITEM_NAME, "my_item_name")
        }
    }

    override fun onAttachedToWindow() {
        setTaskDescriptionColor(getColor(R.color.bg_task_description_color))
        super.onAttachedToWindow()
    }

    companion object {
        fun makeIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}