package ru.crazy_what.bmstu_shedule.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups")
data class GroupDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
)
