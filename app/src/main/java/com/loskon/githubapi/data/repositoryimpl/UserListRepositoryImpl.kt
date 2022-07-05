package com.loskon.githubapi.data.repositoryimpl

import com.loskon.githubapi.data.networkdatasource.NetworkDataSource
import com.loskon.githubapi.data.networkdatasource.dto.toUserModel
import com.loskon.githubapi.domain.model.UserModel
import com.loskon.githubapi.domain.repository.UserListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserListRepositoryImpl(
    private val networkDataSource: NetworkDataSource
) : UserListRepository {

    override suspend fun getUsersAsFlow(pageSize: Int, since: Int): Flow<List<UserModel>> {
        return networkDataSource.getUsersPairAsFlow(pageSize, since).map { pair ->
            pair.second.map { it.toUserModel(fromCache = pair.first) }
        }
    }
}