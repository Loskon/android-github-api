package com.loskon.features.util.network

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val connectionManagerModule = module {
    single { ConnectionManager(androidApplication()) }
}