package com.loskon.githubapi

import android.app.Application
import com.loskon.base.utils.ThemeChanger
import com.loskon.database.databaseModule
import com.loskon.features.repoinfo.repoInfoModule
import com.loskon.features.settings.settingsModule
import com.loskon.features.userlist.userListModule
import com.loskon.features.userprofile.userProfileModule
import com.loskon.features.util.connect.connectionManagerModule
import com.loskon.features.util.preference.AppPref
import com.loskon.network.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ThemeChanger.activateDarkMode(AppPref.hasDarkMode(this))
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    connectionManagerModule,
                    databaseModule,
                    networkModule,
                    userListModule,
                    userProfileModule,
                    repoInfoModule,
                    settingsModule
                )
            )
        }
    }
}