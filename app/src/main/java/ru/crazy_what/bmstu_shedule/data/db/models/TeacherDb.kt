package ru.crazy_what.bmstu_shedule.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teachers")
data class TeacherDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
)
