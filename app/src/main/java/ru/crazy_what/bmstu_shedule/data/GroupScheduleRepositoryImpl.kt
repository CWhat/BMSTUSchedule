package ru.crazy_what.bmstu_shedule.data
/*
import ru.crazy_what.bmstu_shedule.domain.model.Group
import ru.crazy_what.bmstu_shedule.common.ResponseResult
import ru.crazy_what.bmstu_shedule.common.handleApi
import ru.crazy_what.bmstu_shedule.data.remote.ScheduleService
import ru.crazy_what.bmstu_shedule.date.DayOfWeek
import ru.crazy_what.bmstu_shedule.date.Time
import ru.crazy_what.bmstu_shedule.date.WeekType
import ru.crazy_what.bmstu_shedule.domain.model.GroupLesson
import ru.crazy_what.bmstu_shedule.domain.model.GroupSchedule
import ru.crazy_what.bmstu_shedule.domain.model.GroupScheduleImpl
import ru.crazy_what.bmstu_shedule.domain.model.SimpleSchedule
import ru.crazy_what.bmstu_shedule.domain.repository.GroupScheduleRepository
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import ru.crazy_what.bmstu_shedule.data.remote.model.Group as RemoteGroup
import ru.crazy_what.bmstu_shedule.data.remote.model.GroupSchedule as RemoteGroupSchedule

// TODO переписать
class GroupScheduleRepositoryImpl @Inject constructor(
    private val scheduleService: ScheduleService,
) : GroupScheduleRepository {

    // TODO небезопасно, может читаться из нескольких потоков
    private var groups: List<RemoteGroup>? = null

    override suspend fun getSchedule(
        uuid: String,
    ): ResponseResult<GroupSchedule> {
        when (val locGroups = getGroups()) {
            is ResponseResult.Error -> return ResponseResult.error(locGroups.message)
            is ResponseResult.Success -> {
                val group = locGroups.data.firstOrNull { it.name == groupName }
                    ?: return ResponseResult.error("Not found group $groupName")

                return when (val scheduleResult =
                    handleApi { scheduleService.getGroupSchedule(group.uuid) }) {
                    is ResponseResult.Error -> ResponseResult.error(scheduleResult.message)
                    is ResponseResult.Success ->
                        ResponseResult.success(scheduleResult.data.toGroupSchedule())
                }
            }
        }
    }

    private suspend fun getGroups(): ResponseResult<List<RemoteGroup>> {
        // TODO опасно
        if (groups != null)
            return ResponseResult.success(groups!!)

        return when (val result = handleApi { scheduleService.getGroups() }) {
            is ResponseResult.Success -> {
                val networkGroups = result.data
                groups = networkGroups
                ResponseResult.success(networkGroups)
            }

            is ResponseResult.Error -> ResponseResult.error(result.message)
        }
    }

    override suspend fun getAllGroups(): ResponseResult<List<Group>> {
        return when (val result = getGroups()) {
            is ResponseResult.Success -> ResponseResult.success(result.data.map { it.name })
            is ResponseResult.Error -> ResponseResult.error(result.message)
        }
    }

    companion object {

        private val formatter = SimpleDateFormat("yyyyMMdd")

        // TODO стоит обрабатывать ошибки
        private fun parseTime(str: String) = Time(
            hours = str.slice(0..1).toInt(),
            minutes = str.slice(3..4).toInt(),
        )

        fun RemoteGroupSchedule.toGroupSchedule(): GroupSchedule {

            val lessons = mutableMapOf<Pair<WeekType, DayOfWeek>, MutableList<GroupLesson>>()

            for (lesson in this.lessons) {
                val weekType = if (lesson.isNumerator) WeekType.NUMERATOR else WeekType.DENOMINATOR
                val dayOfWeek = DayOfWeek.values()[lesson.day]
                val date = Pair(weekType, dayOfWeek)

                val groupLesson = GroupLesson(
                    begin = parseTime(lesson.startAt),
                    end = parseTime(lesson.endAt),
                    name = lesson.name,
                    type = lesson.type,
                    teacher = lesson.teachers.joinToString(),
                    cabinet = lesson.cabinet,
                )

                if (lessons[date] == null) {
                    lessons[date] = mutableListOf(groupLesson)
                } else {
                    lessons[date]!!.add(groupLesson)
                }
            }

            return GroupScheduleImpl(
                start = Calendar.getInstance().apply {
                    time = formatter.parse(this@toGroupSchedule.semesterStart)!!
                },
                end = Calendar.getInstance().apply {
                    time = formatter.parse(this@toGroupSchedule.semesterEnd)!!
                },
                currentTime = Calendar.getInstance(),
                schedule = SimpleSchedule(
                    groupName = this.group.name,
                    lessons = lessons,
                )
            )
        }

    }
}*/