package com.loskon.githubapi

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.loskon.base.countdowntimer.ShortCountDownTimer

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { true }
    }

    override fun onStart() {
        super.onStart()
        createSplashTimer().start()
    }

    private fun createSplashTimer(): CountDownTimer {
        return ShortCountDownTimer(SPLASH_DURATION) {
            startActivity(MainActivity.makeIntent(this))
            supportFinishAfterTransition()
        }
    }

    companion object {
        private const val SPLASH_DURATION = 600L
    }
}