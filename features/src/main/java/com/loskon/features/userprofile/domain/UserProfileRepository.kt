package com.loskon.features.userprofile.domain

import com.loskon.features.model.RepoModel
import com.loskon.features.model.UserModel

interface UserProfileRepository {

    suspend fun getUser(login: String): UserModel

    suspend fun getRepos(login: String): List<RepoModel>

    suspend fun setUserInCache(user: UserModel)

    suspend fun setReposInCache(login: String, repos: List<RepoModel>)

    suspend fun getCachedUser(login: String): UserModel?

    suspend fun getCachedRepos(login: String): List<RepoModel>?
}