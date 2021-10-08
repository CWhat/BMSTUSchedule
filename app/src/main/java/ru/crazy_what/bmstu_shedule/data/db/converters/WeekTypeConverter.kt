package ru.crazy_what.bmstu_shedule.data.db.converters

import androidx.room.TypeConverter
import ru.crazy_what.bmstu_shedule.data.schedule.WeekType

class WeekTypeConverter {
    @TypeConverter
    fun fromDayOfWeek(value: WeekType): Int {
        return value.ordinal
    }

    @TypeConverter
    fun toDayOfWeak(value: Int): WeekType {
        return WeekType.values()[value]
    }
}