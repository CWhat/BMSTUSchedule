package ru.crazy_what.bmstu_shedule.data.shedule.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import ru.crazy_what.bmstu_shedule.data.shedule.Scheduler

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

    override suspend fun groups(): List<String> {
        if (!groupsMapIsInitialized)
            initGroupsMap()

        val groupsName = groupsMap.keys

        // TODO возможно надо сортировать
        return groupsName.toList()
    }

    override suspend fun schedule(group: String): Scheduler {
        if (!groupsMapIsInitialized)
            initGroupsMap()

        TODO("Not yet implemented")
    }
}