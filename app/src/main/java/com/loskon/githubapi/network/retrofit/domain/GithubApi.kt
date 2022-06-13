package com.loskon.githubapi.network.retrofit.domain

import com.loskon.githubapi.network.retrofit.data.dto.CharacterDto
import com.loskon.githubapi.network.retrofit.data.dto.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    // TODO
    @GET("/api/v2/Characters")
    suspend fun getCharacters(): Response<List<CharacterDto>>


    @GET("/api/v2/Characters/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<CharacterDto>

    // TODO
    @GET("users")
    suspend fun getUsers(@Query("per_page") pageSize: Int): Response<List<UserDto>>

    @GET("search/users?q=repos:>1")
    suspend fun getUsers(@Query("page") page: Int, @Query("per_page") pageSize: Int): Response<List<UserDto>>

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): Response<UserDto>
}