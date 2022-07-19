package com.borchik.infrastructure.utils

import java.text.DateFormat

class DateFormatUtils {

    fun formatDateShort(timestamp: Long): String =
        DateFormat.getDateInstance(DateFormat.SHORT)
            .format(timestamp)

    fun formatTimeShort(timestamp: Long): String =
        DateFormat.getTimeInstance(DateFormat.SHORT)
            .format(timestamp)
}