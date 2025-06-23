package com.loskon.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserSearchDto(
    @Json(name = "total_count")
    val login: Long? = null,
    @Json(name = "incomplete_results")
    val incompleteResults: Boolean? = null,
    @Json(name = "items")
    val items: List<UserDto> =  emptyList()
)