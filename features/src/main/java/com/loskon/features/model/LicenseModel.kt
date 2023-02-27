package com.loskon.features.model

import android.os.Parcelable
import com.loskon.network.dto.LicenseDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class LicenseModel(
    val key: String = "",
    val name: String = "",
    val spdxId: String = "",
    val url: String = "",
    val nodeId: String = ""
) : Parcelable

fun LicenseDto.toLicenseModel(): LicenseModel {
    return LicenseModel(
        key = key ?: "",
        name = name ?: "",
        spdxId = spdxId ?: "",
        url = url ?: "",
        nodeId = nodeId ?: ""
    )
}