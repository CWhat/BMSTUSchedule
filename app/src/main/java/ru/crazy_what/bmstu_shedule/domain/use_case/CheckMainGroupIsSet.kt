package ru.crazy_what.bmstu_shedule.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.crazy_what.bmstu_shedule.common.Resource
import javax.inject.Inject

class CheckMainGroupIsSet @Inject constructor(

) {

    operator fun invoke(): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        // TODO сделать
        emit(Resource.Success(true))
    }

}