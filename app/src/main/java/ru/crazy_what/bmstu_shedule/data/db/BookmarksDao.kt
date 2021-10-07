package ru.crazy_what.bmstu_shedule.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.crazy_what.bmstu_shedule.common.Constants
import ru.crazy_what.bmstu_shedule.domain.model.Bookmark

@Dao
interface BookmarksDao {

    @Insert
    suspend fun addBookmark(bookmark: Bookmark)

    @Delete
    suspend fun deleteBookmark(bookmark: Bookmark)

    @Query("SELECT * FROM ${Constants.BOOKMARKS_DATABASE}")
    suspend fun getAllBookmarks(): List<Bookmark>

    // TODO добавить методы для поиска

}