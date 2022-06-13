package com.loskon.githubapi.app.features.userprofile.domain

import com.loskon.githubapi.network.retrofit.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {

    suspend fun getUser(username: String): Flow<UserModel>
}