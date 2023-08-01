package ru.crazy_what.bmstu_shedule.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.crazy_what.bmstu_shedule.common.Constants

@Entity(tableName = Constants.BOOKMARKS_DATABASE)
data class BookmarkDb(
    @PrimaryKey val uuid: String
)
