package ru.crazy_what.bmstu_shedule.domain.use_case

import ru.crazy_what.bmstu_shedule.domain.repository.BookmarksRepository
import javax.inject.Inject

class DeleteBookmark @Inject constructor(
    private val bookmarksRepository: BookmarksRepository
) {

    // TODO стоит проверять, что есть группа с заданным uuid, и кидать ошибку
    suspend operator fun invoke(uuid: String) = bookmarksRepository.deleteBookmark(uuid)

}