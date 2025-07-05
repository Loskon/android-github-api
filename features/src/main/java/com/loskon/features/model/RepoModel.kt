package com.loskon.features.model

import android.os.Parcelable
import com.loskon.database.entity.RepoEntity
import com.loskon.network.dto.RepoDto
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class RepoModel(
    val id: Long = 0L,
    val name: String = "",
    val htmlUrl: String = "",
    val description: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val pushedAt: LocalDateTime = LocalDateTime.now(),
    val size: Long = 0L,
    val language: String = ""
) : Parcelable

fun RepoDto.toRepoModel(): RepoModel {
    return RepoModel(
        id = id ?: 0L,
        name = name ?: "",
        htmlUrl = htmlUrl ?: "",
        description = description ?: "",
        createdAt = createdAt ?: LocalDateTime.now(),
        updatedAt = updatedAt ?: LocalDateTime.now(),
        pushedAt = pushedAt ?: LocalDateTime.now(),
        size = size ?: 0L,
        language = language ?: ""
    )
}

fun RepoEntity.toRepoModel(): RepoModel {
    return RepoModel(
        id = id,
        name = fullName,
        htmlUrl = htmlUrl,
        description = description,
        createdAt = createdAt,
        updatedAt = updatedAt,
        pushedAt = pushedAt,
        size = size,
        language = language
    )
}

fun RepoModel.toRepoEntity(login: String): RepoEntity {
    return RepoEntity(
        login = login,
        id = id,
        fullName = name,
        htmlUrl = htmlUrl,
        description = description,
        createdAt = createdAt,
        updatedAt = updatedAt,
        pushedAt = pushedAt,
        size = size,
        language = language
    )
}