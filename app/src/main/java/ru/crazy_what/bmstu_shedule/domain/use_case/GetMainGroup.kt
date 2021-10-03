package ru.crazy_what.bmstu_shedule.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.crazy_what.bmstu_shedule.common.Resource

// Возвращает название основной группы
class GetMainGroup {

    operator fun invoke(): Flow<Resource<String>> = flow {
        // TODO сделать
        emit(Resource.Loading())
        emit(Resource.Success("ФН2-32Б"))
    }
}