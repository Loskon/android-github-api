package com.loskon.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class RepositoryDto(
    @Json(name = "id")
    val id: Long? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "html_url")
    val htmlUrl: String? = null,
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "created_at")
    val createdAt: LocalDateTime? = null,
    @Json(name = "updated_at")
    val updatedAt: LocalDateTime? = null,
    @Json(name = "pushed_at")
    val pushedAt: LocalDateTime? = null,
    @Json(name = "size")
    val size: Long? = null,
    @Json(name = "language")
    val language: String? = null
)