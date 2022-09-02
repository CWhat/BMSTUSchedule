package ru.crazy_what.bmstu_shedule.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.crazy_what.bmstu_shedule.common.Resource
import javax.inject.Inject

// TODO возможно, этот UseCase должен возвращать не имя группы, а расписание
// Возвращает название основной группы
class GetMainGroup @Inject constructor(){

    operator fun invoke(): Flow<Resource<String>> = flow {
        // TODO сделать
        emit(Resource.Loading())
        emit(Resource.Success("ФН2-52Б"))
    }

}