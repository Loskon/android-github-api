package com.loskon.features.userprofile.domain

import com.loskon.features.model.RepositoryModel
import com.loskon.features.model.UserModel

interface UserProfileRepository {

    suspend fun getUser(username: String): UserModel

    suspend fun getRepositories(username: String): List<RepositoryModel>
}