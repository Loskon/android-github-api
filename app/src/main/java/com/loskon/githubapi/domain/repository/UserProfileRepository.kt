package com.loskon.githubapi.domain.repository

import com.loskon.githubapi.domain.model.RepositoryModel
import com.loskon.githubapi.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {

    suspend fun getUser(username: String): Flow<UserModel>

    suspend fun getRepositories(username: String): Flow<List<RepositoryModel>>
}