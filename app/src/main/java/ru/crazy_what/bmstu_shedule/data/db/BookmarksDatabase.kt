package ru.crazy_what.bmstu_shedule.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.crazy_what.bmstu_shedule.data.db.models.BookmarkDb

@Database(entities = [BookmarkDb::class], version = 1)
abstract class BookmarksDatabase : RoomDatabase() {

    abstract fun bookmarksDao() : BookmarksDao

}