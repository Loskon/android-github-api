package com.loskon.features.model

import com.loskon.database.entity.UserEntity
import com.loskon.database.entity.UserInfoEntity
import com.loskon.network.dto.RepoDto
import com.loskon.network.dto.UserDto
import java.time.LocalDateTime

data class UserModel(
    val id: Int = 0,
    val login: String = "",
    val avatarUrl: String = "",
    val htmlUrl: String = "",
    val type: String = "",
    val name: String = "",
    val location: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    // own
    val repos: List<RepoModel> = emptyList(),
    val fromCache: Boolean = false
)

fun UserDto.toUserModel(): UserModel {
    return UserModel(
        id = id ?: 0,
        login = login ?: "",
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
        login = login,
        id = id,
        avatarUrl = avatarUrl,
        htmlUrl = htmlUrl,
        type = type,
        createdAt = createdAt
    )
}

fun UserModel.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        login = login,
        avatarUrl = avatarUrl,
        htmlUrl = htmlUrl,
        type = type,
        createdAt = createdAt,
        stars = 0
    )
}

fun UserInfoEntity.toUserModel(): UserModel {
    return UserModel(
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

fun UserModel.toUserInfoEntity(): UserInfoEntity {
    return UserInfoEntity(
        id = id,
        login = login,
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
        id = id ?: 0,
        login = login ?: "",
        avatarUrl = avatarUrl ?: "",
        htmlUrl = htmlUrl ?: "",
        type = type ?: "",
        createdAt = createdAt ?: LocalDateTime.now(),
        stars = 0
    )
}

fun RepoDto.toUserEntity(): UserEntity {
    return UserEntity(
        id = stars,
        login = fullName,
        avatarUrl = "",
        htmlUrl = "",
        type = "",
        createdAt = createdAt ?: LocalDateTime.now(),
        stars = 0
    )
}