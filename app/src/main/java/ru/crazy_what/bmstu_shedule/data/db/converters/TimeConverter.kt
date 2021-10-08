package ru.crazy_what.bmstu_shedule.data.db.converters

import androidx.room.TypeConverter
import ru.crazy_what.bmstu_shedule.date.Time

class TimeConverter {

    @TypeConverter
    fun fromDayOfWeek(value: Time): Int = value.hours * 60 + value.minutes

    @TypeConverter
    fun toDayOfWeak(value: Int): Time = Time(hours = value / 60, minutes = value % 60)

}