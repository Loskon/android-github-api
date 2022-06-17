package com.loskon.githubapi.base.datetime

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Date

const val FULL_DATE_TIME = "yyyy-MM-dd HH:mm"

fun Date.toLocalDate(): LocalDate {
    return toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
}

fun Date.toLocalDateTime(): LocalDateTime {
    return toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
}

fun LocalDate.toDate(): Date {
    return Date.from(atStartOfDay(ZoneId.systemDefault()).toInstant())
}

fun LocalDateTime.toDate(): Date {
    return Date(atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
}

fun LocalDateTime.toFormattedString(): String {
    return format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT))
}

fun LocalDateTime.toFormattedString(pattern: String = FULL_DATE_TIME): String {
    return format(
        when (pattern) {
            FULL_DATE_TIME -> DateTimeFormatter.ofPattern(FULL_DATE_TIME)
            else -> DateTimeFormatter.ofPattern(pattern)
        }
    )
}
