package com.loskon.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.loskon.database.entity.RepoEntity
import com.loskon.database.entity.UserEntity
import com.loskon.database.entity.UserInfoEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    suspend fun getCachedUsers(): List<UserEntity>?

    @Query("SELECT * FROM users_info WHERE login = (:login)")
    suspend fun getCachedUser(login: String): UserInfoEntity?

    @Query("SELECT * FROM repos WHERE login = (:login)")
    suspend fun getCachedRepos(login: String): List<RepoEntity>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun setUsersInCache(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun setUserInCache(user: UserInfoEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun setReposInCache(repoEntities: List<RepoEntity>)
}