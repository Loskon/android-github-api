package com.loskon.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    val userId: Int,
    @ColumnInfo(name = "prev_key")
    val prevKey: Int? = null,
    @ColumnInfo(name = "next_key")
    val nextKey: Int? = null
)
