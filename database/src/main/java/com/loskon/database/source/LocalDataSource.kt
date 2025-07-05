package com.loskon.database.source

import com.loskon.database.dao.UserDao
import com.loskon.database.db.UserDatabase
import com.loskon.database.entity.RepoEntity
import com.loskon.database.entity.UserEntity
import com.loskon.database.entity.UserInfoEntity
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class LocalDataSource(
    private val userDatabase: UserDatabase
) {

    private val userDao: UserDao = userDatabase.userDao()

    suspend fun setUsersInCache(users: List<UserEntity>) {
        userDao.setUsersInCache(users)
    }

    suspend fun setUserInCache(user: UserInfoEntity) {
        userDao.setUserInCache(user)
    }

    suspend fun setReposInCache(repos: List<RepoEntity>) {
        userDao.setReposInCache(repos)
    }

    suspend fun getCachedUsers(): List<UserEntity>? {
        return userDao.getCachedUsers()
    }

    suspend fun getCachedUser(login: String): UserInfoEntity? {
        return userDao.getCachedUser(login)
    }

    suspend fun getCachedRepos(login: String): List<RepoEntity>? {
        return userDao.getCachedRepos(login)
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