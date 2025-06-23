package com.loskon.database.source

import androidx.paging.PagingSource
import com.loskon.database.dao.RemoteKeyDao
import com.loskon.database.dao.UserDao
import com.loskon.database.db.UserDatabase
import com.loskon.database.entity.RepositoryEntity
import com.loskon.database.entity.UserEntity
import com.loskon.database.entity.UserInfoEntity
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class LocalDataSource(
    private val userDao: UserDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val userDatabase: UserDatabase
) {

    suspend fun getCachedUsers(): List<UserEntity>? {
        return userDao.getCachedUsers()
    }

    suspend fun setUsers(users: List<UserEntity>) {
        userDao.insertUsers(users)
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

/*        userDatabase.withTransaction {
                remoteKeyDao.clearRemoteKey()
                userDao.clearUsers()
        }*/
    }

    suspend fun insertAll(response: List<UserEntity>) {
        userDao.insertUsers(response)
/*        userDatabase.withTransaction {
            val key = response.map { RemoteKeyEntity(it.id, it.id) }.last()

            remoteKeyDao.insertKey(key)
            userDao.insertUsers(response)
        }*/
    }

    fun getUsers(): PagingSource<Int, UserEntity> {
        return userDao.getUsers()
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