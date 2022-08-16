package ru.crazy_what.bmstu_shedule.data.remote.schedule

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import ru.crazy_what.bmstu_shedule.common.Constants
import ru.crazy_what.bmstu_shedule.data.schedule.WeekType
import ru.crazy_what.bmstu_shedule.date.DayOfWeek
import ru.crazy_what.bmstu_shedule.date.toTime
import ru.crazy_what.bmstu_shedule.domain.model.GroupLesson
import ru.crazy_what.bmstu_shedule.domain.model.GroupSchedule
import ru.crazy_what.bmstu_shedule.domain.model.LessonInfo

class SchedulerServiceImpl : SchedulerService {

    private var groupsMapIsInitialized = false
    private val groupsMap = mutableMapOf<String, String>()

    private suspend fun initGroupsMap() {
        val map =
            withContext(Dispatchers.IO) {
                val url = Constants.BASE_URL + "/schedule/list"

                val document = Jsoup.connect(url).get()
                //val groupsElements = document.select(".text-nowrap") // все кнопки
                //val groupsElements = document.select("span.text-nowrap") // неактивные кнопки
                val groupsElements = document.select("a.text-nowrap") // только активные кнопки
                val groupsMap = mutableMapOf<String, String>()

                for (el in groupsElements)
                    groupsMap[el.text()] = Constants.BASE_URL + el.attr("href")

                return@withContext groupsMap
            }
        groupsMap += map
        groupsMapIsInitialized = true
    }

    override suspend fun groups(): ResponseResult<List<String>> {
        // TODO возможно, в случае ошибки надо выдавать нормальные сообщения
        try {
            if (!groupsMapIsInitialized)
                initGroupsMap()
        } catch (t: Throwable) {
            return ResponseResult.error(t.message ?: "")
        }

        val groupsName = groupsMap.keys

        return ResponseResult.success(groupsName.toList())
    }

    // TODO выглядит слишком страшно, надо бы отрефакторить
    override suspend fun schedule(group: String): ResponseResult<GroupSchedule> {
        // TODO возможно, в случае ошибки надо выдавать нормальные сообщения
        try {
            if (!groupsMapIsInitialized)
                initGroupsMap()
        } catch (t: Throwable) {
            return ResponseResult.error(t.message ?: "")
        }

        if (!groupsMap.containsKey(group))
            return ResponseResult.error("Группа $group не найдена")

        val url = groupsMap[group]
        val result = withContext(Dispatchers.IO) {
            val document = Jsoup.connect(url).get()
            // Парсим таблицу для мобильных устройств, потому что она проще устроена
            val tables = document.select("div.hidden-sm > table > tbody")
            if (tables.size != 6) {
                return@withContext ResponseResult.error("Ошибка при парсинге расписания по ссылке $url")
            }

            val lessonsMap = mutableMapOf<Pair<WeekType, DayOfWeek>, List<GroupLesson>>()
            // В воскресенье не занятий
            for (dayOfWeekOrdinal in 0 until DayOfWeek.values().size - 1) {
                val dayOfWeek = DayOfWeek.values()[dayOfWeekOrdinal]
                val (numLessons, denomLessons) = tableToLessonsList(
                    table = tables[dayOfWeekOrdinal],
                    dayOfWeek = dayOfWeek
                )
                lessonsMap[Pair(WeekType.NUMERATOR, dayOfWeek)] = numLessons
                lessonsMap[Pair(WeekType.DENOMINATOR, dayOfWeek)] = denomLessons
            }

            return@withContext ResponseResult.success(
                GroupSchedule(
                    groupName = group,
                    lessons = lessonsMap,
                )
            )
        }
        return result
    }

    private fun tdToLessonDto(
        td: Element,
        time: String,
        weekType: WeekType,
        dayOfWeek: DayOfWeek
    ): GroupLesson {
        if (td.childrenSize() != 4)
            error("Я не понимаю такое расписание")

        val (beginTime, endTime) = time.split(' ', limit = 2)

        var itemText = td.child(0).text()
        val type = if (itemText.startsWith('(') && itemText.endsWith(')')) {
            // Это тип пары
            // Убираем первую и последнюю скобку
            itemText.substring(1, itemText.length - 1)
        } else if (itemText.isBlank()) ""
        else {
            itemText
        }

        // Второй элемент - это название предмета
        itemText = td.child(1).text()
        val name = itemText

        // Третий элемент - это кабинет
        itemText = td.child(2).text()
        val cabinet = if (itemText.isBlank()) "" else itemText

        // Четвертый - это преподаватель
        itemText = td.child(3).text()
        val teachers = if (itemText.isBlank()) "" else itemText

        return GroupLesson(
            info = LessonInfo(
                weekType = weekType,
                dayOfWeek = dayOfWeek,
                beginTime = beginTime.toTime(),
                endTime = endTime.toTime(),
                type = type,
                name = name,
                cabinet = cabinet,
            ),
            teachers = listOf(teachers),

            )
    }

    private fun tableToLessonsList(
        table: Element,
        dayOfWeek: DayOfWeek,
    ): Pair<List<GroupLesson>, List<GroupLesson>> {
        val dayOfNumerator = mutableListOf<GroupLesson>()
        val dayOfDenominator = mutableListOf<GroupLesson>()

        // Получем строки
        val trs = table.getElementsByTag("tr")
        // Пропускаем первые две строки, потому что там строки с днем недели и
        // с подписями числителя и знаменателя
        for (i in 2 until trs.size) {
            val tr = trs[i]
            // Получаем столбцы
            val tds = tr.getElementsByTag("td")
            val time = tds[0].text() // текст с временем

            if (tds.size == 3) {
                // Возможно, есть расписание и на числитель, и на знаменатель
                val numeratorPair = tds[1]
                if (numeratorPair.text().isNotBlank()) {
                    val lesson =
                        tdToLessonDto(
                            numeratorPair,
                            time,
                            weekType = WeekType.NUMERATOR,
                            dayOfWeek = dayOfWeek,
                        )
                    dayOfNumerator.add(lesson)
                }
                val denominatorPair = tds[2]
                if (denominatorPair.text().isNotBlank()) {
                    val lesson = tdToLessonDto(
                        denominatorPair,
                        time,
                        weekType = WeekType.DENOMINATOR,
                        dayOfWeek = dayOfWeek,
                    )
                    dayOfDenominator.add(lesson)
                }

            } else {
                // расписание и на числитель, и на знаменатель одинаковое
                val pair = tds[1]
                val numLesson = tdToLessonDto(pair, time, WeekType.NUMERATOR, dayOfWeek)
                val denomLesson = GroupLesson(
                    info = LessonInfo(
                        weekType = WeekType.DENOMINATOR,
                        dayOfWeek = dayOfWeek,
                        beginTime = numLesson.info.beginTime,
                        endTime = numLesson.info.endTime,
                        type = numLesson.info.type,
                        name = numLesson.info.name,
                        cabinet = numLesson.info.cabinet,
                    ),
                    teachers = numLesson.teachers,
                )

                dayOfNumerator.add(numLesson)
                dayOfDenominator.add(denomLesson)
            }
        }

        return Pair(dayOfNumerator, dayOfDenominator)
    }
}