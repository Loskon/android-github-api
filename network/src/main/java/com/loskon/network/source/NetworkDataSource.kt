package com.loskon.network.source

import com.loskon.network.api.GithubApi
import com.loskon.network.dto.RepoDto
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

    suspend fun getSearchUser(page: Int, perPage: Int): List<UserDto> {
        val response = githubApi.getSearchUser(page = page, perPage = perPage)

        return if (response.isSuccessful) {
            response.body()?.items ?: throw IOException("Empty body")
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

    suspend fun getRepos(username: String): List<RepoDto> {
        val response = githubApi.getRepos(username)

        return if (response.isSuccessful) {
            response.body() ?: throw IOException("Empty body")
        } else {
            throw IOException("Http status code: " + response.code())
        }
    }

    suspend fun getSearchRepos(page: Int, perPage: Int): List<RepoDto> {
        val response = githubApi.getSearchRepos(query = "Android", page = page, perPage = perPage)

        return if (response.isSuccessful) {
            response.body()?.items ?: throw IOException("Empty body")
        } else {
            throw IOException("Http status code: " + response.code())
        }
    }
}