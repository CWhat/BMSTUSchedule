package ru.crazy_what.bmstu_shedule.domain.repository

import ru.crazy_what.bmstu_shedule.domain.model.Group
import ru.crazy_what.bmstu_shedule.common.ResponseResult
import ru.crazy_what.bmstu_shedule.domain.model.GroupSchedule

interface GroupScheduleRepository {

    // TODO возвращать SimpleGroupSchedule
    // TODO нужно искать по id или возвращать список
    suspend fun getSchedule(uuid: String): ResponseResult<GroupSchedule>

    suspend fun getAllGroups(): ResponseResult<List<Group>>

    // TODO реализовать
    // checkUpdates
    // loadUpdates
}