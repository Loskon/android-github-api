package com.loskon.githubapi.app.features.users.data

import com.loskon.githubapi.app.features.users.domain.UserListRepository
import com.loskon.githubapi.network.retrofit.data.NetworkDataSource
import com.loskon.githubapi.network.retrofit.data.dto.toUserModel
import com.loskon.githubapi.network.retrofit.domain.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserListRepositoryImpl(
    private val networkDataSource: NetworkDataSource
) : UserListRepository {

    override suspend fun getUsersAsFlow(): Flow<Pair<Boolean, List<UserModel>>> {
        return networkDataSource.getUsersAsFlow().map { pair -> pair.first to pair.second.map { it.toUserModel() } }
    }
}