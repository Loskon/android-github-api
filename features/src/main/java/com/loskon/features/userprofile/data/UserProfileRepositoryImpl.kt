package com.loskon.features.userprofile.data

import com.loskon.database.source.LocalDataSource
import com.loskon.features.model.RepositoryModel
import com.loskon.features.model.UserModel
import com.loskon.features.model.toRepositoryEntity
import com.loskon.features.model.toRepositoryModel
import com.loskon.features.model.toUserInfoEntity
import com.loskon.features.model.toUserModel
import com.loskon.features.userprofile.domain.UserProfileRepository
import com.loskon.network.source.NetworkDataSource

class UserProfileRepositoryImpl(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource
) : UserProfileRepository {

    override suspend fun getUser(login: String): UserModel {
        return networkDataSource.getUser(login).toUserModel()
    }

    override suspend fun getRepositories(login: String): List<RepositoryModel> {
        return networkDataSource.getRepositories(login).map { it.toRepositoryModel() }
    }

    override suspend fun setUser(user: UserModel) {
        localDataSource.setUser(user.toUserInfoEntity())
    }

    override suspend fun setRepositories(login: String, repositories: List<RepositoryModel>) {
        localDataSource.setRepositories(repositories.map { it.toRepositoryEntity(login) })
    }

    override suspend fun getCachedUser(login: String): UserModel? {
        return localDataSource.getCachedUser(login)?.toUserModel()
    }

    override suspend fun getCachedRepositories(login: String): List<RepositoryModel>? {
        return localDataSource.getCachedRepositories(login)?.map { it.toRepositoryModel() }
    }
}