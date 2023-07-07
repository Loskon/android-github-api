package com.loskon.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.loskon.database.convertor.DateTimeConverter
import com.loskon.database.dao.UserDao
import com.loskon.database.entity.RepoEntity
import com.loskon.database.entity.UserEntity

@Database(entities = [UserEntity::class, RepoEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateTimeConverter::class)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}