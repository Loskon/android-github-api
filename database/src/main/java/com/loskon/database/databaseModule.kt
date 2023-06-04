package com.loskon.database

import android.content.Context
import androidx.room.Room
import com.loskon.database.dao.UserDao
import com.loskon.database.db.UserDatabase
import com.loskon.database.source.DatabaseDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(androidContext()) }
    single { provideDao(get()) }

    single { DatabaseDataSource(get()) }
}

private const val DATABASE_NAME = "github_database"

private fun provideDatabase(context: Context): UserDatabase {
    return Room.databaseBuilder(
        context.applicationContext,
        UserDatabase::class.java,
        DATABASE_NAME
    ).build()
}

private fun provideDao(database: UserDatabase): UserDao {
    return database.userDao()
}