package ru.crazy_what.bmstu_shedule.domain.use_case

import ru.crazy_what.bmstu_shedule.domain.model.Bookmark
import ru.crazy_what.bmstu_shedule.domain.repository.BookmarksRepository
import javax.inject.Inject

class DeleteBookmark @Inject constructor(
    private val bookmarksRepository: BookmarksRepository
) {

    // TODO стоит проверять, что bookmark.groupName не пусто и кидать ошибку
    suspend operator fun invoke(bookmark: Bookmark) = bookmarksRepository.deleteBookmark(bookmark)

}