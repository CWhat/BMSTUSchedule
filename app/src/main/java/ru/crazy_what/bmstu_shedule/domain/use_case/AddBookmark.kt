package ru.crazy_what.bmstu_shedule.domain.use_case

import ru.crazy_what.bmstu_shedule.domain.model.Bookmark
import ru.crazy_what.bmstu_shedule.domain.repository.BookmarksRepository
import javax.inject.Inject

class AddBookmark @Inject constructor(
    private val bookmarksRepository: BookmarksRepository
) {

    suspend operator fun invoke(bookmark: Bookmark) = bookmarksRepository.addBookmark(bookmark)

}