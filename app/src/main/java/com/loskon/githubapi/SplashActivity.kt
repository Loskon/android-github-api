package com.loskon.githubapi

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepVisibleCondition { true }
    }

    override fun onStart() {
        super.onStart()
        createSplashTimer().start()
    }

    private fun createSplashTimer(): CountDownTimer {
        return com.loskon.base.countdowntimer.ShortCountDownTimer(SPLASH_DURATION) {
            startActivity(MainActivity.makeIntent(this))
            supportFinishAfterTransition()
        }
    }

    companion object {
        private const val SPLASH_DURATION = 600L
    }
}