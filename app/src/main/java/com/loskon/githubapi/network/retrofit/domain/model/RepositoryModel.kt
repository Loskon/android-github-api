package com.loskon.githubapi.network.retrofit.domain.model

import java.util.Date

data class RepositoryModel(
    val id: Long = 0L,
    val name: String = "",
    val fullName: String = "",
    val htmlUrl: String = "",
    val description: String = "",
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val pushedAt: Date = Date(),
    val size: Long = 0L,
    val language: String = "",
    val license: LicenseModel = LicenseModel(),
    val topics: MutableList<String> = arrayListOf()
)