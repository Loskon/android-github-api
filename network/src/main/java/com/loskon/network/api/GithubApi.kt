package com.loskon.network.api

import com.loskon.network.BuildConfig
import com.loskon.network.dto.RepositoryDto
import com.loskon.network.dto.UserDto
import com.loskon.network.dto.UserSearchDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") pageSize: Int
    ): Response<List<UserDto>>

    @GET("search/users?sort=joined")
    suspend fun getUserSearch(
        @Query("q") since: String = "type:user",
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Response<UserSearchDto>

    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): Response<UserDto>

    @GET("users/{username}/repos")
    suspend fun getRepositories(
        @Path("username") username: String
    ): Response<List<RepositoryDto>>
}