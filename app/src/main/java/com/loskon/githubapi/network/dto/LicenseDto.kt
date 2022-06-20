package com.loskon.githubapi.network.dto

import com.google.gson.annotations.SerializedName
import com.loskon.githubapi.network.model.LicenseModel

data class LicenseDto(
    @SerializedName("key") val key: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("spdx_id") val spdxId: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("node_id") val nodeId: String? = null
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