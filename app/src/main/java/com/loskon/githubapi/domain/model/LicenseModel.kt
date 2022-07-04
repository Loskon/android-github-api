package com.loskon.githubapi.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LicenseModel(
    val key: String = "",
    val name: String = "",
    val spdxId: String = "",
    val url: String = "",
    val nodeId: String = ""
) : Parcelable