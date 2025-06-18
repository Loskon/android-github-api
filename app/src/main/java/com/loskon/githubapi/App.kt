package com.loskon.githubapi

import android.app.Application
import com.loskon.base.utils.ColorUtil
import com.loskon.database.databaseModule
import com.loskon.features.repositoryinfo.repoInfoModule
import com.loskon.features.settings.settingsModule
import com.loskon.features.userlist.userListModule
import com.loskon.features.userprofile.userProfileModule
import com.loskon.features.util.network.connectManagerModule
import com.loskon.features.util.preference.AppPreference
import com.loskon.network.networkModule
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
                    networkModule,
                    userListModule,
                    userProfileModule,
                    repoInfoModule,
                    connectManagerModule,
                    databaseModule,
                    settingsModule
                )
            )
        }
    }
}