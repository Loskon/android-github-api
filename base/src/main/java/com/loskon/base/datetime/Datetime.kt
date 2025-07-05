package com.loskon.base.datetime

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun LocalDateTime.toFormatString(): String {
    return format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT))
}