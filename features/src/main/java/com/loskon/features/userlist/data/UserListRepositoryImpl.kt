package com.loskon.features.userlist.data

import com.loskon.database.source.LocalDataSource
import com.loskon.features.model.UserModel
import com.loskon.features.model.toUserEntity
import com.loskon.features.model.toUserModel
import com.loskon.features.userlist.domain.UserListRepository
import com.loskon.network.source.NetworkDataSource

class UserListRepositoryImpl(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource
) : UserListRepository {

    override suspend fun getUsers(pageSize: Int, since: Int): List<UserModel> {
        return networkDataSource.getUsers(pageSize, since).map { it.toUserModel() }
    }

    override suspend fun getCachedUsers(): List<UserModel>? {
        return localDataSource.getCachedUsers()?.map { it.toUserModel() }
    }

    override suspend fun setUsers(users: List<UserModel>) {
        localDataSource.setUsers(users.map { it.toUserEntity() })
    }
}