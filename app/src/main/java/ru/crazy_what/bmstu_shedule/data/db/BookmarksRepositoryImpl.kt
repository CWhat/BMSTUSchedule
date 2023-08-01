package ru.crazy_what.bmstu_shedule.data.db

import ru.crazy_what.bmstu_shedule.domain.model.Group
import ru.crazy_what.bmstu_shedule.domain.repository.BookmarksRepository
import javax.inject.Inject

// TODO реализовать
class BookmarksRepositoryImpl @Inject constructor(
    //private val dao: BookmarksDao,
) : BookmarksRepository {

    //override suspend fun addBookmark(bookmark: Bookmark) = dao.addBookmark(bookmark)
    override suspend fun addBookmark(uuid: String) {}

    //override suspend fun deleteBookmark(bookmark: Bookmark) = dao.deleteBookmark(bookmark)
    override suspend fun deleteBookmark(uuid: String) {}

    //override suspend fun getAllBookmarks(): List<Bookmark> = dao.getAllBookmarks()
    override suspend fun getAllBookmarks(): List<Group> = emptyList()

}