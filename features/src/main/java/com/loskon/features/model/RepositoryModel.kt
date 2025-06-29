package com.loskon.features.model

import android.os.Parcelable
import com.loskon.database.entity.RepositoryEntity
import com.loskon.network.dto.RepoDto
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class RepositoryModel(
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

fun RepoDto.toRepositoryModel(): RepositoryModel {
    return RepositoryModel(
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

fun RepositoryEntity.toRepositoryModel(): RepositoryModel {
    return RepositoryModel(
        id = id ?: 0L,
        name = fullName ?: "",
        htmlUrl = htmlUrl ?: "",
        description = description ?: "",
        createdAt = createdAt ?: LocalDateTime.now(),
        updatedAt = updatedAt ?: LocalDateTime.now(),
        pushedAt = pushedAt ?: LocalDateTime.now(),
        size = size ?: 0L,
        language = language ?: ""
    )
}

fun RepositoryModel.toRepositoryEntity(login: String): RepositoryEntity {
    return RepositoryEntity(
        id = id,
        fullName = name,
        htmlUrl = htmlUrl,
        description = description,
        createdAt = createdAt,
        updatedAt = updatedAt,
        pushedAt = pushedAt,
        size = size,
        language = language,
        login = login
    )
}