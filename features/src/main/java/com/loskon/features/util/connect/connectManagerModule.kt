package com.loskon.features.util.connect

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val connectManagerModule = module {
    single { ConnectManager(androidApplication()) }
}