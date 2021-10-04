package ru.crazy_what.bmstu_shedule.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.crazy_what.bmstu_shedule.common.Constants

@Entity(tableName = Constants.BOOKMARKS_DATABASE)
data class Bookmark(
    @PrimaryKey val groupName: String
)
