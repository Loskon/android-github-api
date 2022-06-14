package com.loskon.githubapi.network.retrofit.domain

import com.loskon.githubapi.network.retrofit.data.dto.RepositoryDto
import com.loskon.githubapi.network.retrofit.data.dto.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    // TODO
    @GET("users")
    suspend fun getUsers(@Query("per_page") pageSize: Int): Response<List<UserDto>>

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): Response<UserDto>

    @GET("users/{username}/repos")
    suspend fun getRepositories(@Path("username") username: String): Response<List<RepositoryDto>>
}