package com.loskon.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "repositories")
data class RepositoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long? = null,
    @ColumnInfo(name = "full_name")
    val fullName: String? = null,
    @ColumnInfo(name = "html_url")
    val htmlUrl: String? = null,
    @ColumnInfo(name = "description")
    val description: String? = null,
    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime? = null,
    @ColumnInfo(name = "updated_at")
    val updatedAt: LocalDateTime? = null,
    @ColumnInfo(name = "pushed_at")
    val pushedAt: LocalDateTime? = null,
    @ColumnInfo(name = "size")
    val size: Long? = null,
    @ColumnInfo(name = "language")
    val language: String? = null
)