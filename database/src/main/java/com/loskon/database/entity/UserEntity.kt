package com.loskon.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "login")
    val login: String,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,
    @ColumnInfo(name = "html_url")
    val htmlUrl: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime,
    @ColumnInfo(name = "stars")
    val stars: Int
)