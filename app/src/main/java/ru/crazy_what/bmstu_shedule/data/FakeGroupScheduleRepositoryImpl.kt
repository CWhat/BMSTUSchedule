package ru.crazy_what.bmstu_shedule.data

import ru.crazy_what.bmstu_shedule.common.ResponseResult
import ru.crazy_what.bmstu_shedule.domain.model.Group
import ru.crazy_what.bmstu_shedule.domain.model.GroupSchedule
import ru.crazy_what.bmstu_shedule.domain.repository.GroupScheduleRepository

// TODO можно сделать логику посложнее
class FakeGroupScheduleRepositoryImpl() : GroupScheduleRepository {

    private val groups = listOf(Group("ФН2-32Б", "0"))

    override suspend fun getAllGroups(): ResponseResult<List<Group>> =
        ResponseResult.success(groups)

    override suspend fun getSchedule(uuid: String): ResponseResult<GroupSchedule> =
        if (uuid == "0")
            ResponseResult.success(fakeData("ФН2-32Б", uuid))
        else
            ResponseResult.error("invalid uuid")
}