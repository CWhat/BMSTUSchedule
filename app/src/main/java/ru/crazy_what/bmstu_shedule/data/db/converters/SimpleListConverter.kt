package ru.crazy_what.bmstu_shedule.data.db.converters

import androidx.room.TypeConverter

class SimpleListConverter {

    @TypeConverter
    fun fromTeachers(groups: List<String>): String = groups.joinToString(separator = ",")

    @TypeConverter
    fun toGroups(value: String): List<String> = value.split(",")

}