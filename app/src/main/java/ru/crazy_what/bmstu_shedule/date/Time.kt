package ru.crazy_what.bmstu_shedule.date

import androidx.annotation.IntRange

data class Time(
    @IntRange(from = 0, to = 23) val hours: Int,
    @IntRange(from = 0, to = 59) val minutes: Int,
) {

    val absMinutes: Int
        get() = hours * 60 + minutes

    override fun toString(): String =
        "${if (hours < 10) "0" else ""}$hours:${if (minutes < 10) "0" else ""}$minutes"

    operator fun compareTo(other: Time): Int =
        if (this.hours != other.hours) this.hours.compareTo(other.hours)
        else this.minutes.compareTo(other.minutes)

    // TODO Что делать, если количество часов перевалит за 24?
    operator fun plus(right: Time) = Time(
        this.hours + right.hours + (this.minutes + right.minutes) / 60,
        (this.minutes + right.minutes) % 60,
    )

    // Предполагается, что левое время всегда больше правого
    operator fun minus(right: Time): Time {
        val diffMin = this.minutes - right.minutes
        return if (diffMin >= 0) Time(this.hours - right.hours, diffMin)
        else Time(this.hours - right.hours - 1, 60 - diffMin)
    }
}

// TODO стоит добавить проверку ошибок
// TODO можно добавить проверку на количество символов (маскимум 5)
fun String.toTime(delimiter: String = ":"): Time {
    val list = this.split(delimiter)
    return Time(list[0].toInt(), list[1].toInt())
}
