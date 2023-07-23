package com.loskon.network.api

import com.loskon.network.BuildConfig
import com.loskon.network.dto.RepoDto
import com.loskon.network.dto.SearchUserDto
import com.loskon.network.dto.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("users")
    suspend fun getUsers(@Query("per_page") pageSize: Int, @Query("since") since: Int): Response<List<UserDto>>

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): Response<UserDto>

    @GET("users/{username}/repos")
    suspend fun getRepositories(@Path("username") username: String): Response<List<RepoDto>>

    @Headers("Authorization: ${BuildConfig.ACCESS_TOKEN}")
    @GET("search/users")
    suspend fun getSearchUsers(
        @Query("q") since: String = "type:user",
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Response<SearchUserDto>
}