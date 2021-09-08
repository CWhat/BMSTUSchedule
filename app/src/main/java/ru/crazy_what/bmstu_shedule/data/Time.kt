package ru.crazy_what.bmstu_shedule.data

import androidx.annotation.IntRange

data class Time(
    @IntRange(from = 0, to = 23) val hours: Int,
    @IntRange(from = 0, to = 59) val minutes: Int
) {

    override fun toString(): String =
        "${if (hours < 10) "0" else ""}$hours:${if (minutes < 10) "0" else ""}$minutes"
}
