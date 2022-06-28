package com.loskon.githubapi.app

import android.app.Application
import com.loskon.githubapi.BuildConfig
import com.loskon.githubapi.app.features.repositoryinfo.repoInfoModule
import com.loskon.githubapi.app.features.userlist.userListModule
import com.loskon.githubapi.app.features.userprofile.userProfileModule
import com.loskon.githubapi.network.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

@Suppress("unused")
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        initializeKoin(this)
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