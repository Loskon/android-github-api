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

    override suspend fun getUsersPairAsFlow(): Flow<Pair<Boolean, List<UserModel>>> {
        return networkDataSource.getUsersAsFlow().map { pair -> pair.first to pair.second.map { it.toUserModel() } }
    }
}