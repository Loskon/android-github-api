package com.loskon.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.loskon.database.convertor.DateTimeConverter
import com.loskon.database.dao.UserDao
import com.loskon.database.entity.RepositoryEntity
import com.loskon.database.entity.UserEntity
import com.loskon.database.entity.UserInfoEntity

@Database(
    entities = [
        UserEntity::class,
        UserInfoEntity::class,
        RepositoryEntity::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(DateTimeConverter::class)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}