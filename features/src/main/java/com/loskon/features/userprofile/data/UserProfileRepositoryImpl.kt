package com.loskon.features.userprofile.data

import com.loskon.features.model.RepositoryModel
import com.loskon.features.model.UserModel
import com.loskon.features.model.toRepositoryModel
import com.loskon.features.model.toUserModel
import com.loskon.features.userprofile.domain.UserProfileRepository
import com.loskon.network.source.NetworkDataSource

class UserProfileRepositoryImpl(
    private val networkDataSource: NetworkDataSource
) : UserProfileRepository {

    override suspend fun getUser(username: String): UserModel {
        return networkDataSource.getUser(username).toUserModel()
    }

    override suspend fun getRepositories(username: String): List<RepositoryModel> {
        return networkDataSource.getRepositories(username).map { it.toRepositoryModel() }
    }
}