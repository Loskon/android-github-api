package com.loskon.network.source

import com.loskon.network.api.GithubApi
import com.loskon.network.dto.RepositoryDto
import com.loskon.network.dto.UserDto

class NetworkDataSource(
    private val githubApi: GithubApi
) {

    suspend fun getUser(username: String): UserDto {
        val response = githubApi.getUser(username)

        return if (response.isSuccessful) {
            response.body() ?: UserDto()
        } else {
            UserDto()
        }
    }

    suspend fun getRepositories(username: String): List<RepositoryDto> {
        val response = githubApi.getRepositories(username)

        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            emptyList()
        }
    }
}