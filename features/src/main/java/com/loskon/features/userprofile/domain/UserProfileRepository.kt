package com.loskon.features.userprofile.domain

import com.loskon.features.model.RepositoryModel
import com.loskon.features.model.UserModel

interface UserProfileRepository {

    suspend fun getUser(login: String): UserModel

    suspend fun getRepositories(login: String): List<RepositoryModel>

    suspend fun setUser(user: UserModel)

    suspend fun getCachedUser(login: String): UserModel?

    suspend fun getCachedRepositories(login: String): List<RepositoryModel>?

    suspend fun setRepositories(login: String, repositories: List<RepositoryModel>)
}