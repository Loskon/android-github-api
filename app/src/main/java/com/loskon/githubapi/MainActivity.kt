package com.loskon.githubapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.ktx.Firebase
import com.loskon.base.countdowntimer.ShortCountDownTimer
import com.loskon.base.extension.activity.setTaskDescriptionColor

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private var keep: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition { keep }
        ShortCountDownTimer(SPLASH_DURATION) { keep = false }.start()
        super.onCreate(savedInstanceState)

        Firebase.analytics.logEvent(FirebaseAnalytics.Event.APP_OPEN) {
            param(FirebaseAnalytics.Param.ITEM_NAME, "my_item_name")
        }
    }

    override fun onAttachedToWindow() {
        setTaskDescriptionColor(getColor(R.color.bg_task_description_color))
        super.onAttachedToWindow()
    }

    companion object {

        private const val SPLASH_DURATION = 800L
    }
}