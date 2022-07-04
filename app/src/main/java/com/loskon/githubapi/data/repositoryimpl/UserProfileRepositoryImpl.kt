package com.loskon.githubapi.data.repositoryimpl

import com.loskon.githubapi.data.networkdatasource.dto.toRepositoryModel
import com.loskon.githubapi.data.networkdatasource.dto.toUserModel
import com.loskon.githubapi.data.networkdatasource.NetworkDataSource
import com.loskon.githubapi.domain.model.RepositoryModel
import com.loskon.githubapi.domain.model.UserModel
import com.loskon.githubapi.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserProfileRepositoryImpl(
    private val networkDataSource: NetworkDataSource
) : UserProfileRepository {

    override suspend fun getUser(username: String): Flow<UserModel> {
        return networkDataSource.getUserPairAsFlow(username).map { pair ->
            pair.second.toUserModel(pair.first)
        }
    }

    override suspend fun getRepositories(username: String): Flow<List<RepositoryModel>> {
        return networkDataSource.getRepositoriesPairAsFlow(username).map { pair ->
            pair.second.map { it.toRepositoryModel() }
        }
    }
}