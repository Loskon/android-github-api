package com.loskon.githubapi.data.networkdatasource.dto

import com.loskon.githubapi.domain.model.LicenseModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LicenseDto(
    @Json(name = "key")
    val key: String? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "spdx_id")
    val spdxId: String? = null,
    @Json(name = "url")
    val url: String? = null,
    @Json(name = "node_id")
    val nodeId: String? = null
)

fun LicenseDto.toLicenseModel(): LicenseModel {
    return LicenseModel(
        key = key ?: "",
        name = name ?: "",
        spdxId = spdxId ?: "",
        url = url ?: "",
        nodeId = nodeId ?: ""
    )
}