package com.loskon.network.source

import com.loskon.network.api.GithubApi
import com.loskon.network.dto.RepositoryDto
import com.loskon.network.dto.UserDto
import java.io.IOException

class NetworkDataSource(
    private val githubApi: GithubApi
) {

    suspend fun getUsers(since: Int, pageSize: Int): List<UserDto> {
        val response = githubApi.getUsers(since, pageSize)

        return if (response.isSuccessful) {
            response.body() ?: throw IOException("Empty body")
        } else {
            throw IOException("Http status code: " + response.code())
        }
    }

    suspend fun getUser(username: String): UserDto {
        val response = githubApi.getUser(username)

        return if (response.isSuccessful) {
            response.body() ?: throw IOException("Empty body")
        } else {
            throw IOException("Http status code: " + response.code())
        }
    }

    suspend fun getRepositories(username: String): List<RepositoryDto> {
        val response = githubApi.getRepositories(username)

        return if (response.isSuccessful) {
            response.body() ?: throw IOException("Empty body")
        } else {
            throw IOException("Http status code: " + response.code())
        }
    }
}