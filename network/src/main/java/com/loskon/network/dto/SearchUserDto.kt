package com.loskon.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchUserDto(
    @Json(name = "total_count")
    val login: Long? = null,
    @Json(name = "incomplete_results")
    val incompleteResults: Boolean? = null,
    @Json(name = "items")
    val items: List<UserDto> =  emptyList()
)

@JsonClass(generateAdapter = true)
data class SearchRepoDto(
    @Json(name = "total_count")
    val total: Int = 0,
    @Json(name = "items")
    val items: List<RepoDto>? = null
)