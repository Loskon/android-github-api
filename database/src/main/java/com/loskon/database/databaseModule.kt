package com.loskon.database

import android.content.Context
import androidx.room.Room
import com.loskon.database.db.UserDatabase
import com.loskon.database.source.LocalDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(androidContext()) }
    single { LocalDataSource(get()) }
}

private fun provideDatabase(context: Context): UserDatabase {
    return Room.databaseBuilder(
        context.applicationContext,
        UserDatabase::class.java,
        BuildConfig.DATABASE_NAME
    ).build()
}