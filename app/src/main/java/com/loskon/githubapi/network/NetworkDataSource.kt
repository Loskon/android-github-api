package com.loskon.githubapi.network

import com.loskon.githubapi.network.CacheInterceptor.Companion.CACHE_HEADER
import com.loskon.githubapi.network.dto.RepositoryDto
import com.loskon.githubapi.network.dto.UserDto
import com.loskon.githubapi.network.exception.NoSuccessfulException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NetworkDataSource(
    private val githubApi: GithubApi
) {

    suspend fun getUsersPairAsFlow(pageSize: Int = 30): Flow<Pair<Boolean, List<UserDto>>> {
        return flow {
            val response = githubApi.getUsers(pageSize)

            if (response.isSuccessful) {
                val fromCache = response.headers()[CACHE_HEADER].toBoolean()
                emit(fromCache to (response.body() ?: emptyList()))
            } else {
                throw NoSuccessfulException(response.code())
            }
        }
    }

    suspend fun getUserPairAsFlow(username: String): Flow<Pair<Boolean, UserDto>> {
        return flow {
            val response = githubApi.getUser(username)

            if (response.isSuccessful) {
                val fromCache = response.headers()[CACHE_HEADER].toBoolean()
                emit(fromCache to (response.body() ?: UserDto()))
            } else {
                throw NoSuccessfulException(response.code())
            }
        }
    }

    suspend fun getRepositoriesPairAsFlow(username: String): Flow<Pair<Boolean, List<RepositoryDto>>> {
        return flow {
            val response = githubApi.getRepositories(username)

            if (response.isSuccessful) {
                val fromCache = response.headers()[CACHE_HEADER].toBoolean()
                emit(fromCache to (response.body() ?: emptyList()))
            } else {
                throw NoSuccessfulException(response.code())
            }
        }
    }
}