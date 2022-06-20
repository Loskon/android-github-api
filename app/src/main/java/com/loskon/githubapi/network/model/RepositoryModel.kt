package com.loskon.githubapi.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class RepositoryModel(
    val id: Long = 0L,
    val name: String = "",
    val fullName: String = "",
    val htmlUrl: String = "",
    val description: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val pushedAt: LocalDateTime = LocalDateTime.now(),
    val size: Long = 0L,
    val language: String = "",
    val license: LicenseModel = LicenseModel(),
    val topics: MutableList<String> = arrayListOf()
) : Parcelable