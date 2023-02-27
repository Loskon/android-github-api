package com.loskon.network.moshiadapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeMoshiAdapter {
    private val formatter = DateTimeFormatter.ISO_DATE_TIME

    @ToJson
    fun toJson(dateTime: LocalDateTime?): String? {
        return dateTime?.let {
            ZonedDateTime.of(it, ZoneId.systemDefault()).toLocalDateTime().toString()
        }
    }

    @FromJson
    fun fromJson(json: String?): LocalDateTime? {
        return json?.let {
            ZonedDateTime.parse(it, formatter).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
        }
    }
}