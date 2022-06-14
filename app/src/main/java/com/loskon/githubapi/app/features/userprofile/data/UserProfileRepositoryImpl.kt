package com.loskon.githubapi.app.features.userprofile.data

import com.loskon.githubapi.app.features.userprofile.domain.UserProfileRepository
import com.loskon.githubapi.network.retrofit.data.NetworkDataSource
import com.loskon.githubapi.network.retrofit.data.dto.toRepositoryModel
import com.loskon.githubapi.network.retrofit.data.dto.toUserModel
import com.loskon.githubapi.network.retrofit.domain.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class UserProfileRepositoryImpl(
    private val networkDataSource: NetworkDataSource
) : UserProfileRepository {

    override suspend fun getUser(username: String): Flow<UserModel> {
        val userFlow = networkDataSource.getUserAsFlow(username)
        val repositoriesFlow = networkDataSource.getRepositoriesAsFlow(username)

        return combine(userFlow, repositoriesFlow) { user, repositories ->
            repositories.second.map { it.toRepositoryModel() }
            user.toUserModel()
        }

        // return networkDataSource.getUserAsFlow(username).map { it.toUserModel() }
    }
}