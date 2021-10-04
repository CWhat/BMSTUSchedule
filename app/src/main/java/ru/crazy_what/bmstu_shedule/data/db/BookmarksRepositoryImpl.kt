package ru.crazy_what.bmstu_shedule.data.db

import ru.crazy_what.bmstu_shedule.domain.model.Bookmark
import ru.crazy_what.bmstu_shedule.domain.repository.BookmarksRepository
import javax.inject.Inject

class BookmarksRepositoryImpl @Inject constructor(
    private val dao: BookmarksDao
) : BookmarksRepository {

    override suspend fun addBookmark(bookmark: Bookmark) = dao.addBookmark(bookmark)

    override suspend fun deleteBookmark(bookmark: Bookmark) = dao.deleteBookmark(bookmark)

    override suspend fun getAllBookmarks(): List<Bookmark> = dao.getAllBookmarks()

}