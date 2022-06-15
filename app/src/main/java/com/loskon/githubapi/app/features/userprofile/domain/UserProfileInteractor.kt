package com.loskon.githubapi.app.features.userprofile.domain

import com.loskon.githubapi.network.retrofit.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class UserProfileInteractor(
    private val userProfileRepository: UserProfileRepository
) {

    suspend fun getUser(username: String): Flow<UserModel> {
        return userProfileRepository.getUser(username)
    }

    suspend fun getUser1(username: String): Flow<UserModel> {
        val userFlow = userProfileRepository.getUser1(username)
        val repositoriesFlow = userProfileRepository.getRepositories(username)

        return combine(userFlow, repositoriesFlow) { user, repositories ->
            user.copy(repositories = repositories)
        }
    }
}