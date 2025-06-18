package com.loskon.features.model

import com.loskon.database.entity.UserEntity
import com.loskon.database.entity.UserInfoEntity
import com.loskon.network.dto.UserDto
import java.time.LocalDateTime

data class UserModel(
    val login: String = "",
    val id: Long = 0L,
    val avatarUrl: String = "",
    val htmlUrl: String = "",
    val type: String = "",
    val name: String = "",
    val location: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    // own
    val repositories: List<RepositoryModel> = emptyList(),
    val fromCache: Boolean = false
)

fun UserDto.toUserModel(): UserModel {
    return UserModel(
        login = login ?: "",
        id = id ?: 0,
        avatarUrl = avatarUrl ?: "",
        htmlUrl = htmlUrl ?: "",
        type = type ?: "",
        name = name ?: "",
        location = location ?: "",
        createdAt = createdAt ?: LocalDateTime.now()
    )
}

fun UserEntity.toUserModel(): UserModel {
    return UserModel(
        login = login ?: "",
        id = id ?: 0,
        avatarUrl = avatarUrl ?: "",
        htmlUrl = htmlUrl ?: "",
        type = type ?: "",
        createdAt = createdAt ?: LocalDateTime.now()
    )
}

fun UserModel.toUserEntity(): UserEntity {
    return UserEntity(
        login = login,
        id = id,
        avatarUrl = avatarUrl,
        htmlUrl = htmlUrl,
        type = type,
        createdAt = createdAt
    )
}

fun UserInfoEntity.toUserModel(): UserModel {
    return UserModel(
        login = login ?: "",
        id = id ?: 0,
        avatarUrl = avatarUrl ?: "",
        htmlUrl = htmlUrl ?: "",
        type = type ?: "",
        name = name ?: "",
        location = location ?: "",
        createdAt = createdAt ?: LocalDateTime.now()
    )
}

fun UserModel.toUserInfoEntity(): UserInfoEntity {
    return UserInfoEntity(
        login = login,
        id = id,
        avatarUrl = avatarUrl,
        htmlUrl = htmlUrl,
        type = type,
        name = name,
        location = location,
        createdAt = createdAt
    )
}

fun UserDto.toUserEntity(): UserEntity {
    return UserEntity(
        login = login ?: "",
        id = id ?: 0,
        avatarUrl = avatarUrl ?: "",
        htmlUrl = htmlUrl ?: "",
        type = type ?: "",
        createdAt = createdAt ?: LocalDateTime.now()
    )
}