package ru.crazy_what.bmstu_shedule.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.crazy_what.bmstu_shedule.common.Resource
import javax.inject.Inject

// Возвращает UUID основной группы
class GetMainGroup @Inject constructor(
    // TODO
    //private val settingsRepository: SettingsRepository,
) {

    // Возвращает null, если основная группа не установлена, иначе uuid основной группы
    // Неплохо было бы, если возвращало null, если группа некорректна (отсутсвует такой uuid в БД)
    operator fun invoke(): Flow<Resource<String?>> = flow {
        emit(Resource.Loading())
        emit(Resource.Success("0"))
    }

    // TODO раскомментировать, когда реализую выбор основной группы
    // есть подозрения, что это не работает
    /*operator fun invoke(): Flow<Resource<String?>> =
        flowOf<Resource<String?>>(Resource.Loading()).onCompletion {
            emitAll(settingsRepository.getMainGroupUuid().map { Resource.Success(it) })
        }*/

}