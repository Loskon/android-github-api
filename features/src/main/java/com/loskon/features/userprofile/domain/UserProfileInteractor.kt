package com.loskon.features.userprofile.domain

import com.loskon.features.model.UserModel

class UserProfileInteractor(
    private val userProfileRepository: UserProfileRepository
) {

    suspend fun getUser(login: String): UserModel {
        val user = userProfileRepository.getUser(login)
        val repositories = userProfileRepository.getRepositories(login)

        return user.copy(repositories = repositories.sortedBy { it.name })
    }

    suspend fun getCachedUser(login: String): UserModel? {
        val user = userProfileRepository.getCachedUser(login)
        val repositories = userProfileRepository.getCachedRepositories(login)

        return user?.copy(repositories = repositories?.sortedBy { it.name } ?: emptyList())
    }

    suspend fun setUser(user: UserModel) {
        userProfileRepository.setUser(user)
        userProfileRepository.setRepositories(user.login, user.repositories)
    }
}