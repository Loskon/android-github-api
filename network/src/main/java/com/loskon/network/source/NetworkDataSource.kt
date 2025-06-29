package com.loskon.network.source

import com.loskon.network.api.GithubApi
import com.loskon.network.dto.RepoDto
import com.loskon.network.dto.UserDto
import okio.IOException

class NetworkDataSource(
    private val githubApi: GithubApi
) {

    suspend fun getUsers(
        since: Int = 0,
        pageSize: Int = 100
    ): List<UserDto> {
        val response = githubApi.getUsers(since, pageSize)

        return if (response.isSuccessful) {
            response.body() ?: throw IOException("Empty data")
        } else {
            throw IOException("Http error code" + response.code())
        }
    }

    suspend fun getUser(username: String): UserDto {
        val response = githubApi.getUser(username)

        return if (response.isSuccessful) {
            response.body() ?: throw IOException("Empty data")
        } else {
            throw IOException("Http error code" + response.code())
        }
    }

    suspend fun getRepos(username: String): List<RepoDto> {
        val response = githubApi.getRepos(username)

        return if (response.isSuccessful) {
            response.body() ?: throw IOException("Empty data")
        } else {
            throw IOException("Http error code" + response.code())
        }
    }
}