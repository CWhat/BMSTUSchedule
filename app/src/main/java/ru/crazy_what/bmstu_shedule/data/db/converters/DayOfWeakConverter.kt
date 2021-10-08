package ru.crazy_what.bmstu_shedule.data.db.converters

import androidx.room.TypeConverter
import ru.crazy_what.bmstu_shedule.date.DayOfWeek

class DayOfWeakConverter {

    @TypeConverter
    fun fromDayOfWeek(value: DayOfWeek): Int {
        return value.ordinal
    }

    @TypeConverter
    fun toDayOfWeak(value: Int): DayOfWeek {
        return DayOfWeek.values()[value]
    }
}