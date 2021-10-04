package ru.crazy_what.bmstu_shedule.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.crazy_what.bmstu_shedule.domain.model.Bookmark

@Database(entities = [Bookmark::class], version = 1)
abstract class BookmarksDatabase : RoomDatabase() {

    abstract fun bookmarksDao() : BookmarksDao

}