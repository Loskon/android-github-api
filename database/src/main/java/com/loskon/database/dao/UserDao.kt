package com.loskon.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.loskon.database.entity.RepositoryEntity
import com.loskon.database.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    suspend fun getCachedUsers(): List<UserEntity>?

    @Query("SELECT * FROM users WHERE login = (:login)")
    suspend fun getUser(login: String): UserEntity?

    @Query("SELECT * FROM repositories WHERE login = (:login)")
    suspend fun getCachedRepositories(login: String): List<RepositoryEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(repository: List<RepositoryEntity>)
}