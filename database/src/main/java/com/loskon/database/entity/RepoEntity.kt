package com.loskon.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "repos")
data class RepoEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "full_name")
    val fullName: String,
    @ColumnInfo(name = "html_url")
    val htmlUrl: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime,
    @ColumnInfo(name = "updated_at")
    val updatedAt: LocalDateTime,
    @ColumnInfo(name = "pushed_at")
    val pushedAt: LocalDateTime,
    @ColumnInfo(name = "size")
    val size: Long,
    @ColumnInfo(name = "language")
    val language: String,
    @ColumnInfo(name = "login")
    val login: String
)