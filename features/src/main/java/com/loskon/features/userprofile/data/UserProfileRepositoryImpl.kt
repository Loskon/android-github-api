package com.loskon.features.userprofile.data

import com.loskon.database.source.LocalDataSource
import com.loskon.features.model.RepoModel
import com.loskon.features.model.UserModel
import com.loskon.features.model.toRepositoryEntity
import com.loskon.features.model.toRepositoryModel
import com.loskon.features.model.toUserEntity
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

    override suspend fun getRepositories(login: String): List<RepoModel> {
        return networkDataSource.getRepositories(login).map { it.toRepositoryModel() }
    }

    override suspend fun setUser(user: UserModel) {
        localDataSource.setUser(user.toUserEntity(fromCache = true))
    }

    override suspend fun setRepositories(login: String, repositories: List<RepoModel>) {
        localDataSource.setRepositories(repositories.map { it.toRepositoryEntity(login) })
    }

    override suspend fun getCachedUser(login: String): UserModel? {
        val user = localDataSource.getCachedUser(login)

        return if (user?.fromCache == false) {
            null
        } else {
            localDataSource.getCachedUser(login)?.toUserModel()
        }
    }

    override suspend fun getCachedRepositories(login: String): List<RepoModel>? {
        return localDataSource.getCachedRepositories(login)?.map { it.toRepositoryModel() }
    }
}