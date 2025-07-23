package com.loskon.features.userprofile.data

import com.loskon.database.source.LocalDataSource
import com.loskon.features.model.RepoModel
import com.loskon.features.model.UserModel
import com.loskon.features.model.toRepoEntity
import com.loskon.features.model.toRepoModel
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

    override suspend fun getRepos(login: String): List<RepoModel> {
        return networkDataSource.getRepos(login).map { it.toRepoModel() }
    }

    override suspend fun setUserInCache(user: UserModel) {
        localDataSource.setUserInCache(user.toUserInfoEntity())
    }

    override suspend fun setReposInCache(login: String, repos: List<RepoModel>) {
        localDataSource.setReposInCache(repos.map { it.toRepoEntity(login) })
    }

    override suspend fun getCachedUser(login: String): UserModel? {
        return localDataSource.getCachedUser(login)?.toUserModel()
    }

    override suspend fun getCachedRepos(login: String): List<RepoModel>? {
        return localDataSource.getCachedRepos(login)?.map { it.toRepoModel() }
    }
}