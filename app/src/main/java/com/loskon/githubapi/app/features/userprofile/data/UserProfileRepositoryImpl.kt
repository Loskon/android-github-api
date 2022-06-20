package com.loskon.githubapi.app.features.userprofile.data

import com.loskon.githubapi.app.features.userprofile.domain.UserProfileRepository
import com.loskon.githubapi.network.NetworkDataSource
import com.loskon.githubapi.network.dto.toRepositoryModel
import com.loskon.githubapi.network.dto.toUserModel
import com.loskon.githubapi.network.model.RepositoryModel
import com.loskon.githubapi.network.model.UserModel
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