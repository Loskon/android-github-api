package com.loskon.features.userprofile.domain

import com.loskon.features.model.UserModel

class UserProfileInteractor(
    private val userProfileRepository: UserProfileRepository
) {

    suspend fun getUser(username: String): UserModel {
        val user = userProfileRepository.getUser(username)
        val repositories = userProfileRepository.getRepositories(username)

        return user.copy(repositories = repositories.sortedBy { it.fullName })
    }
}