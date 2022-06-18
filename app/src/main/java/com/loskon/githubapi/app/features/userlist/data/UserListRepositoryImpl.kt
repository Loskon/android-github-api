package com.loskon.githubapi.app.features.userlist.data

import com.loskon.githubapi.app.features.userlist.domain.UserListRepository
import com.loskon.githubapi.network.retrofit.NetworkDataSource
import com.loskon.githubapi.network.retrofit.dto.toUserModel
import com.loskon.githubapi.network.retrofit.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserListRepositoryImpl(
    private val networkDataSource: NetworkDataSource
) : UserListRepository {

    override suspend fun getUsersAsFlow(): Flow<List<UserModel>> {
        return networkDataSource.getUsersPairAsFlow().map { pair ->
            pair.second.map { it.toUserModel(fromCache = pair.first) }
        }
    }
}