package ru.crazy_what.bmstu_shedule.data.remote.schedule

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import ru.crazy_what.bmstu_shedule.common.Constants
import ru.crazy_what.bmstu_shedule.data.Lesson
import ru.crazy_what.bmstu_shedule.data.mutableListWithCapacity
import ru.crazy_what.bmstu_shedule.data.schedule.*

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
    override suspend fun schedule(group: String): ResponseResult<BiweeklySchedule> {
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

            val numeratorList = mutableListWithCapacity<List<Lesson>>(6)
            val denominatorList = mutableListWithCapacity<List<Lesson>>(6)

            for (table in tables) {
                val dayOfNumerator = mutableListOf<Lesson>()
                val dayOfDenominator = mutableListOf<Lesson>()

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
                            val lesson = tdToLesson(numeratorPair, time)
                            dayOfNumerator.add(lesson)
                        }
                        val denominatorPair = tds[2]
                        if (denominatorPair.text().isNotBlank()) {
                            val lesson = tdToLesson(denominatorPair, time)
                            dayOfDenominator.add(lesson)
                        }

                    } else {
                        // расписание и на числитель, и на знаменатель одинаковое
                        val pair = tds[1]
                        val lesson = tdToLesson(pair, time)

                        dayOfNumerator.add(lesson)
                        dayOfDenominator.add(lesson)
                    }
                }

                numeratorList.add(dayOfNumerator)
                denominatorList.add(dayOfDenominator)
            }

            val numerator = WeekSchedule(
                monday = numeratorList[0],
                tuesday = numeratorList[1],
                wednesday = numeratorList[2],
                thursday = numeratorList[3],
                friday = numeratorList[4],
                saturday = numeratorList[5]
            )

            val denominator = WeekSchedule(
                monday = denominatorList[0],
                tuesday = denominatorList[1],
                wednesday = denominatorList[2],
                thursday = denominatorList[3],
                friday = denominatorList[4],
                saturday = denominatorList[5]
            )

            return@withContext ResponseResult.success(
                BiweeklySchedule(
                    numerator,
                    denominator
                )
            )
        }
        return result
    }

    // TODO конвертировать в новый Lesson
    private fun tdToLesson(td: Element, time: String): Lesson {
        if (td.childrenSize() != 4)
            error("Я не понимаю такое расписание")

        val (timeStart, timeEnd) = time.split(' ', limit = 2)

        var itemText = td.child(0).text()
        val type = if (itemText.isBlank()) null
        else if (itemText.startsWith('(') && itemText.endsWith(')')) {
            // Это тип пары
            // Убираем первую и последнюю скобку
            itemText.substring(1, itemText.length - 1)
        } else {
            itemText
        }

        // Второй элемент - это название предмета
        itemText = td.child(1).text()
        val name = itemText

        // Третий элемент - это кабинет
        itemText = td.child(2).text()
        val room = if (itemText.isNotBlank()) itemText else null

        // Четвертый - это преподаватель
        itemText = td.child(3).text()
        val teacher = if (itemText.isNotBlank()) itemText else null

        return Lesson(
            timeStart = timeStart,
            timeEnd = timeEnd,
            type = type,
            name = name,
            teacher = teacher,
            room = room,
        )
    }
}