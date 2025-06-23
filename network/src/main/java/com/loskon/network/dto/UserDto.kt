package com.loskon.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class UserDto(
    @Json(name = "id")
    val id: Long? = null,
    @Json(name = "login")
    val login: String? = null,
    @Json(name = "avatar_url")
    val avatarUrl: String? = null,
    @Json(name = "html_url")
    val htmlUrl: String? = null,
    @Json(name = "type")
    val type: String? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "location")
    val location: String? = null,
    @Json(name = "created_at")
    val createdAt: LocalDateTime? = null
)