package com.loskon.features.userlist.data

import com.loskon.features.model.UserModel
import com.loskon.features.model.toUserModel
import com.loskon.features.userlist.domain.UserListRepository
import com.loskon.network.source.NetworkDataSource

class UserListRepositoryImpl(
    private val networkDataSource: NetworkDataSource
) : UserListRepository {

    override suspend fun getUsers(pageSize: Int, since: Int): List<UserModel> {
        return networkDataSource.getUsers(pageSize, since).map { it.toUserModel() }
    }
}