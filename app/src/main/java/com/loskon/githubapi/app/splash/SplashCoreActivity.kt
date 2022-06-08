package com.loskon.githubapi.app.splash

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.loskon.githubapi.app.MainActivity
import com.loskon.template.base.countdowntimer.SplashCountDownTimer

/**
 * Новый способ создания SplashScreen от google
 * https://developer.android.com/guide/topics/ui/splash-screen/migrate
 * https://habr.com/ru/post/648535/
 */
class SplashCoreActivity : AppCompatActivity() {

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
        return SplashCountDownTimer(SPLASH_DURATION) {
            startActivity(MainActivity.makeIntent(this))
            supportFinishAfterTransition()
        }
    }

    companion object {
        private const val SPLASH_DURATION = 1000L
    }
}