package ru.crazy_what.bmstu_shedule.data.shedule.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import ru.crazy_what.bmstu_shedule.data.shedule.*

class SchedulerServiceImpl : SchedulerService {

    private var groupsMapIsInitialized = false
    private val groupsMap = mutableMapOf<String, String>()

    // TODO надо как-то обрабатывать ошибки (например, отсутствие сети)
    private suspend fun initGroupsMap() {
        val map =
            withContext(Dispatchers.IO) {
                val url = "https://lks.bmstu.ru/schedule/list"

                val document = Jsoup.connect(url).get()
                //val groupsElements = document.select(".text-nowrap") // все кнопки
                //val groupsElements = document.select("span.text-nowrap") // неактивные кнопки
                val groupsElements = document.select("a.text-nowrap") // только активные кнопки
                val groupsMap = mutableMapOf<String, String>()

                for (el in groupsElements)
                    groupsMap[el.text()] = "https://lks.bmstu.ru${el.attr("href")}"

                return@withContext groupsMap
            }
        groupsMap += map
        groupsMapIsInitialized = true
    }

    override suspend fun groups(): ResponseResult<List<String>> {
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
    override suspend fun schedule(group: String): ResponseResult<Scheduler> {
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

            val numeratorList = mutableListWithCapacity<List<LessonInfo>>(6)
            val denominatorList = mutableListWithCapacity<List<LessonInfo>>(6)

            for (table in tables) {
                val dayOfNumerator = mutableListOf<LessonInfo>()
                val dayOfDenominator = mutableListOf<LessonInfo>()

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
                            val lessonInfo = tdToLessonInfo(numeratorPair, time)
                            dayOfNumerator.add(lessonInfo)

                        }
                        val denominatorPair = tds[2]
                        if (denominatorPair.text().isNotBlank()) {
                            val lessonInfo = tdToLessonInfo(denominatorPair, time)
                            dayOfDenominator.add(lessonInfo)

                        }

                    } else {
                        // расписание и на числитель, и на знаменатель одинаковое
                        val pair = tds[1]
                        val lessonInfo = tdToLessonInfo(pair, time)

                        dayOfNumerator.add(lessonInfo)
                        dayOfDenominator.add(lessonInfo)
                    }
                }

                numeratorList.add(dayOfNumerator)
                denominatorList.add(dayOfDenominator)
            }

            val numerator = StudyWeek(
                monday = numeratorList[0],
                tuesday = numeratorList[1],
                wednesday = numeratorList[2],
                thursday = numeratorList[3],
                friday = numeratorList[4],
                saturday = numeratorList[5]
            )

            val denominator = StudyWeek(
                monday = denominatorList[0],
                tuesday = denominatorList[1],
                wednesday = denominatorList[2],
                thursday = denominatorList[3],
                friday = denominatorList[4],
                saturday = denominatorList[5]
            )

            return@withContext ResponseResult.success(Scheduler(numerator, denominator))
        }
        return result
    }

    private fun tdToLessonInfo(td: Element, time: String): LessonInfo {
        var type: String? = null
        var name = ""
        var room: String? = null
        var teacher: String? = null

        for (el in td.children()) {
            if (el.tagName() == "span") {
                // название пары заключено в <span>...</span>
                name = el.text()
            } else {
                // всё остальное в <i>...</i>
                val text = el.text()

                if (text.isBlank())
                    continue

                if (text.startsWith('(') && text.endsWith(')')) {
                    // Это тип пары
                    // Убираем первую и последнюю скобку
                    type = text.substring(1, text.length - 1)
                } else if (text[0].isDigit() || text == "Каф") {
                    // TODO надо еще парсить Измайлово, но как оно выглядит?
                    // Это аудитория
                    if (text != "Каф") {
                        room = text
                    }
                } else {
                    // Остается только преподаватель
                    teacher = text
                }
            }
        }

        val building = getBuildingFromRoom(room)
        return LessonInfo(
            typeLesson = getTypeLesson(type),
            building = building,
            name = name,
            teacher = teacher,
            room = room,
            numPair = getNumPair(time, building)
        )
    }
}