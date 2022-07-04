package com.loskon.githubapi.data.networkdatasource

import com.loskon.githubapi.data.networkdatasource.api.GithubApi
import com.loskon.githubapi.data.networkdatasource.dto.RepositoryDto
import com.loskon.githubapi.data.networkdatasource.dto.UserDto
import com.loskon.githubapi.domain.exception.NoSuccessfulException
import com.loskon.githubapi.data.networkdatasource.interceptor.CacheInterceptor.Companion.CACHE_HEADER
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

/*    suspend fun getUsersPairAsFlow1(pageSize: Int = 30): Flow<NetworkDataResult<List<UserDto>>> {
        return flow {
            val response = githubApi.getUsers(pageSize)

            if (response.isSuccessful) {
                val fromCache = response.headers()[CACHE_HEADER].toBoolean()
                emit(fromCache to (response.body() ?: emptyList()))
            } else {
                throw NoSuccessfulException(response.code())
            }
        }
    }*/

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