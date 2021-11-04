package ru.crazy_what.bmstu_shedule.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.crazy_what.bmstu_shedule.common.Resource
import ru.crazy_what.bmstu_shedule.domain.repository.GroupScheduleRepository
import javax.inject.Inject

// Очень простая проверка, проверяет есть ли в базе данных какие-нибудь группы
// TODO сделать более надежную проверку
class CheckScheduleIsLoaded @Inject constructor(
    private val groupScheduleRepository: GroupScheduleRepository
) {

    operator fun invoke(): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        val groups = groupScheduleRepository.getAllGroupsName()

        if (groups.isNotEmpty()) {
            emit(Resource.Success(true))
        } else {
            emit(Resource.Success(false))
        }
    }

}