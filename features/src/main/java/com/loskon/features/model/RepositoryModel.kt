package com.loskon.features.model

import android.os.Parcelable
import com.loskon.network.dto.RepositoryDto
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class RepositoryModel(
    val id: Long = 0L,
    val fullName: String = "",
    val htmlUrl: String = "",
    val description: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val pushedAt: LocalDateTime = LocalDateTime.now(),
    val size: Long = 0L,
    val language: String = ""
) : Parcelable

fun RepositoryDto.toRepositoryModel(): RepositoryModel {
    return RepositoryModel(
        id = id ?: 0L,
        fullName = fullName ?: "",
        htmlUrl = htmlUrl ?: "",
        description = description ?: "",
        createdAt = createdAt ?: LocalDateTime.now(),
        updatedAt = updatedAt ?: LocalDateTime.now(),
        pushedAt = pushedAt ?: LocalDateTime.now(),
        size = size ?: 0L,
        language = language ?: ""
    )
}