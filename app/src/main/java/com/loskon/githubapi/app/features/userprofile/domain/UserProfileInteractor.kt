package com.loskon.githubapi.app.features.userprofile.domain

import com.loskon.githubapi.network.retrofit.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

class UserProfileInteractor(
    private val userProfileRepository: UserProfileRepository
) {

    suspend fun getUser(username: String): Flow<UserModel> {
        return userProfileRepository.getUser(username)
    }
}