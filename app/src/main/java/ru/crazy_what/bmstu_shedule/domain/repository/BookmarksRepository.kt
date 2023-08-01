package ru.crazy_what.bmstu_shedule.domain.repository

import ru.crazy_what.bmstu_shedule.domain.model.Group

interface BookmarksRepository {

    suspend fun addBookmark(uuid: String)

    suspend fun deleteBookmark(uuid: String)

    // TODO добавить список преподавателей
    suspend fun getAllBookmarks(): List<Group>

}