package com.loskon.database.convertor

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.TimeZone

class DateTimeConverter {

    @TypeConverter
    fun formatDate(date: LocalDateTime): Long {
        return ZonedDateTime.of(date, ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    @TypeConverter
    fun toDate(millis: Long): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), TimeZone.getDefault().toZoneId())
    }
}