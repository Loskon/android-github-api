package com.loskon.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.loskon.database.entity.RepositoryEntity
import com.loskon.database.entity.UserEntity
import com.loskon.database.entity.UserInfoEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    suspend fun getCachedUsers(): List<UserEntity>?

    @Query("SELECT * FROM users_info WHERE login = (:login)")
    suspend fun getCachedUser(login: String): UserInfoEntity?

    @Query("SELECT * FROM repositories WHERE login = (:login)")
    suspend fun getCachedRepositories(login: String): List<RepositoryEntity>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserInfoEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRepositories(repository: List<RepositoryEntity>)
}