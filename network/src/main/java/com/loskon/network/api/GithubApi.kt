package com.loskon.network.api

import com.loskon.network.dto.RepoDto
import com.loskon.network.dto.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") pageSize: Int
    ): Response<List<UserDto>>

    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): Response<UserDto>

    @GET("users/{username}/repos")
    suspend fun getRepos(
        @Path("username") username: String
    ): Response<List<RepoDto>>
}