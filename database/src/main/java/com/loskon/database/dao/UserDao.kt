package com.loskon.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.loskon.database.entity.RepoEntity
import com.loskon.database.entity.UserEntity
import com.loskon.database.entity.UserInfoEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY id ASC")
    fun getUsers(): PagingSource<Int, UserEntity>

    @Query("SELECT * FROM users")
    suspend fun getCachedUsers(): List<UserEntity>?

    @Query("SELECT * FROM users_info WHERE login = (:login)")
    suspend fun getCachedUser(login: String): UserInfoEntity?

    @Query("SELECT * FROM repos WHERE login = (:login)")
    suspend fun getCachedRepos(login: String): List<RepoEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setUsersInCache(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setUserInCache(user: UserInfoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setReposInCache(repoEntities: List<RepoEntity>)

    @Query("DELETE FROM users")
    suspend fun clearUsers()

    @Query("DELETE FROM repos")
    suspend fun clearRepositories()
}