package com.loskon.githubapi.network.retrofit.data

import com.loskon.githubapi.network.retrofit.data.CacheInterceptor.Companion.CACHE_HEADER
import com.loskon.githubapi.network.retrofit.data.dto.RepositoryDto
import com.loskon.githubapi.network.retrofit.data.dto.UserDto
import com.loskon.githubapi.network.retrofit.data.exception.NoSuccessfulException
import com.loskon.githubapi.network.retrofit.domain.GithubApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import timber.log.Timber

class NetworkDataSource(
    private val githubApi: GithubApi
) {

    suspend fun getUsersAsFlow(pageSize: Int = 10): Flow<Pair<Boolean, List<UserDto>>> {
        return flow {
            val response = githubApi.getUsers(pageSize)

            if (response.isSuccessful) {
                val fromCache = response.headers().get(CACHE_HEADER).toBoolean()
                emit(fromCache to (response.body() ?: emptyList()))
            } else {
                throw NoSuccessfulException(response.code())
            }
        }
    }

    suspend fun getUserAsFlow(username: String): Flow<UserDto> {
        return flow {
            val response = githubApi.getUser(username)

            if (response.isSuccessful) {
                emit(response.body() ?: UserDto())
            } else {
                val jsonObj = JSONObject(response.errorBody()?.charStream()?.readText().toString())
                Timber.d(jsonObj.toString())
                throw NoSuccessfulException(response.code())
            }
        }
    }

    suspend fun getRepositoriesAsFlow(username: String): Flow<Pair<Boolean, List<RepositoryDto>>> {
        return flow {
            val response = githubApi.getRepositories(username)

            if (response.isSuccessful) {
                val fromCache = response.headers().get(CACHE_HEADER).toBoolean()
                emit(false to (response.body() ?: emptyList()))
            } else {
                throw NoSuccessfulException(response.code())
            }
        }
    }
}