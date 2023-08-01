package ru.crazy_what.bmstu_shedule.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.crazy_what.bmstu_shedule.common.Resource
import ru.crazy_what.bmstu_shedule.domain.model.Group
import ru.crazy_what.bmstu_shedule.domain.repository.BookmarksRepository
import javax.inject.Inject

class GetAllBookmarks @Inject constructor(
    private val bookmarksRepository: BookmarksRepository
) {

    // TODO стоит проверять, что есть группа с заданным uuid, и кидать ошибку
    operator fun invoke(): Flow<Resource<List<Group>>> = flow {
        emit(Resource.Loading())
        // TODO здесь стоит обрабатывать ошибки
        val bookmarks = bookmarksRepository.getAllBookmarks()
        emit(Resource.Success(bookmarks))
    }

}