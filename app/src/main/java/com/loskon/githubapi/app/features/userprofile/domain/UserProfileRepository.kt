package com.loskon.githubapi.app.features.userprofile.domain

import com.loskon.githubapi.network.retrofit.model.RepositoryModel
import com.loskon.githubapi.network.retrofit.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {

    suspend fun getUser(username: String): Flow<UserModel>

    suspend fun getRepositories(username: String): Flow<List<RepositoryModel>>
}