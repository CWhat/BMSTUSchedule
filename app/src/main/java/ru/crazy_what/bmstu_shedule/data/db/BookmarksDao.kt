package ru.crazy_what.bmstu_shedule.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.crazy_what.bmstu_shedule.common.Constants
import ru.crazy_what.bmstu_shedule.data.db.models.BookmarkDb

@Dao
interface BookmarksDao {

    // TODO мб нужно использовать uuid (String)
    @Insert
    suspend fun addBookmark(bookmark: BookmarkDb)

    // TODO мб нужно использовать uuid (String)
    @Delete
    suspend fun deleteBookmark(bookmark: BookmarkDb)

    // TODO мб нужно возвращать Group (использовать JOIN)
    @Query("SELECT * FROM ${Constants.BOOKMARKS_DATABASE}")
    suspend fun getAllBookmarks(): List<BookmarkDb>

    // TODO добавить методы для поиска (если это вообще нужно)

}