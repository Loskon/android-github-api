package com.loskon.githubapi.app.splash

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.loskon.githubapi.app.main.MainActivity
import com.loskon.githubapi.utils.AppPreference
import com.loskon.githubapi.utils.ColorHelper
import com.loskon.template.base.countdowntimer.SplashCountDownTimer

class SplashCoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        ColorHelper.installDarkTheme(AppPreference.hasDarkMode(this))
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { true }
    }

    override fun onStart() {
        super.onStart()
        createSplashTimer().start()
    }

    private fun createSplashTimer(): CountDownTimer {
        return SplashCountDownTimer(SPLASH_DURATION) {
            startActivity(MainActivity.makeIntent(this))
            supportFinishAfterTransition()
        }
    }

    companion object {
        private const val SPLASH_DURATION = 600L
    }
}