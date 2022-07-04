package com.loskon.githubapi.app

import android.app.Application
import com.loskon.githubapi.BuildConfig
import com.loskon.githubapi.di.repoInfoModule
import com.loskon.githubapi.di.userListModule
import com.loskon.githubapi.di.userProfileModule
import com.loskon.githubapi.di.networkModule
import com.loskon.githubapi.utils.AppPreference
import com.loskon.githubapi.utils.ColorUtil
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

@Suppress("unused")
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        initializeKoin(this)
        ColorUtil.toggleDarkMode(AppPreference.getHasDarkMode(this))
    }

    private fun initializeKoin(application: Application) {
        startKoin {
            androidContext(application)
            modules(
                listOf(
                    networkModule, userListModule,
                    userProfileModule, repoInfoModule
                )
            )
        }
    }
}