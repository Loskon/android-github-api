package com.loskon.database.dao

import androidx.room.*
import com.loskon.database.entity.RepositoryEntity
import com.loskon.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

/**
 * Profile Dao
 */

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllProfiles(): Flow<List<UserEntity>>

    @Query("SELECT * FROM repositories")
    fun getFavoritesProfiles(): Flow<List<RepositoryEntity>>

    @Query("SELECT * FROM users WHERE id = (:id)")
    fun getUser(id: Long): Flow<UserEntity>

    @Query("SELECT * FROM repositories WHERE id = (:id)")
    fun getRepository(id: Long): Flow<RepositoryEntity>

    @Insert
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Insert
    suspend fun insertRepository(repository: RepositoryEntity)

    @Update
    suspend fun updateRepository(repository: RepositoryEntity)
}