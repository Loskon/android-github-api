package com.loskon.database.source

import com.loskon.database.db.UserDatabase
import com.loskon.database.entity.RepositoryEntity
import com.loskon.database.entity.UserEntity
import com.loskon.database.entity.UserInfoEntity
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class LocalDataSource(
    private val userDatabase: UserDatabase
) {

    private val userDao = userDatabase.userDao()

    suspend fun getCachedUsers(): List<UserEntity>? {
        return userDao.getCachedUsers()
    }

    suspend fun setUsers(users: List<UserEntity>) {
        userDao.setUsers(users)
    }

    suspend fun getCachedUser(login: String): UserInfoEntity? {
        return userDao.getCachedUser(login)
    }

    suspend fun getCachedRepositories(login: String): List<RepositoryEntity>? {
        return userDao.getCachedRepositories(login)
    }

    suspend fun setUser(user: UserInfoEntity) {
        userDao.insertUser(user)
    }

    suspend fun setRepositories(repositories: List<RepositoryEntity>) {
        userDao.insertRepositories(repositories)
    }

    suspend fun clearAll() {
        userDao.clearUsers()
    }

    @Suppress("TooGenericExceptionCaught")
    suspend fun clearData() {
        suspendCoroutine { cont ->
            try {
                userDatabase.clearAllTables()
                cont.resume(Unit)
            } catch (exception: Exception) {
                cont.resumeWithException(exception)
            }
        }
    }
}