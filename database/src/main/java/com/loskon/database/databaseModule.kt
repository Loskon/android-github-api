package com.loskon.database

import android.content.Context
import androidx.room.Room
import com.loskon.database.dao.RemoteKeyDao
import com.loskon.database.dao.UserDao
import com.loskon.database.db.UserDatabase
import com.loskon.database.mediator.LocalRemoteMediator
import com.loskon.database.source.LocalDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(androidContext()) }
    single { provideUserDao(get()) }
    single { provideRemoteKeyDao(get()) }
    single { LocalDataSource(get(), get(), get()) }
    single { LocalRemoteMediator(get()) }
}

private const val DATABASE_NAME = "github.db"

private fun provideDatabase(context: Context): UserDatabase {
    return Room.databaseBuilder(
        context.applicationContext,
        UserDatabase::class.java,
        DATABASE_NAME
    ).build()
}

private fun provideUserDao(database: UserDatabase): UserDao {
    return database.userDao()
}

private fun provideRemoteKeyDao(database: UserDatabase): RemoteKeyDao {
    return database.remoteKeyDao()
}