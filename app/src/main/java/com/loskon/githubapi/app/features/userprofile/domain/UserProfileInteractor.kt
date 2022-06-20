package com.loskon.githubapi.app.features.userprofile.domain

import com.loskon.githubapi.network.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class UserProfileInteractor(
    private val userProfileRepository: UserProfileRepository
) {

    suspend fun getUser(username: String): Flow<UserModel> {
        val userFlow = userProfileRepository.getUser(username)
        val repositoriesFlow = userProfileRepository.getRepositories(username)

        return combine(userFlow, repositoriesFlow) { user, repositories ->
            user.copy(repositories = repositories.sortedBy { it.fullName })
        }
    }
}