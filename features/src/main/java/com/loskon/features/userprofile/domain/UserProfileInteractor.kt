package com.loskon.features.userprofile.domain

import com.loskon.features.model.UserModel

class UserProfileInteractor(
    private val userProfileRepository: UserProfileRepository
) {

    suspend fun getUser(login: String): UserModel {
        val user = userProfileRepository.getUser(login)
        val repos = userProfileRepository.getRepos(login)

        return user.copy(repos = repos.sortedBy { it.name })
    }

    suspend fun getCachedUser(login: String): UserModel? {
        val user = userProfileRepository.getCachedUser(login)
        val repos = userProfileRepository.getCachedRepos(login)

        return user?.copy(repos = repos?.sortedBy { it.name } ?: emptyList())
    }

    suspend fun setUserInCache(user: UserModel) {
        userProfileRepository.setUserInCache(user)
        userProfileRepository.setReposInCache(user.login, user.repos)
    }
}