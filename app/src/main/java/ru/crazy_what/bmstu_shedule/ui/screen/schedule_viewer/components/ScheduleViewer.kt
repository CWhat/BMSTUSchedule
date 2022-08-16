package ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.crazy_what.bmstu_shedule.data.schedule.WeekType
import ru.crazy_what.bmstu_shedule.date.DayOfWeek
import ru.crazy_what.bmstu_shedule.date.Time
import ru.crazy_what.bmstu_shedule.domain.model.GroupScheduleImpl
import ru.crazy_what.bmstu_shedule.domain.model.GroupLesson
import ru.crazy_what.bmstu_shedule.domain.model.GroupSchedule
import ru.crazy_what.bmstu_shedule.domain.model.SimpleGroupSchedule
import ru.crazy_what.bmstu_shedule.ui.base_components.ErrorMessage
import ru.crazy_what.bmstu_shedule.ui.base_components.LoadView
import ru.crazy_what.bmstu_shedule.ui.theme.BMSTUScheduleTheme
import java.util.*

@ExperimentalPagerApi
@Composable
fun ScheduleViewer(scheduleViewerState: ScheduleViewerState) {
    Column(modifier = Modifier.fillMaxSize()) {
        when (scheduleViewerState) {
            is ScheduleViewerState.Loading -> LoadView()
            is ScheduleViewerState.Schedule ->
                GroupScheduleViewer(
                    modifier = Modifier.fillMaxSize(),
                    groupSchedule = scheduleViewerState.scheduler,
                )
            // TODO добавить красивый диалог с ошибкой
            is ScheduleViewerState.Error -> ErrorMessage(
                text = "При загрузке расписания произошла ошибка: ${scheduleViewerState.message}",
                modifier = Modifier
                    .padding(24.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun GroupScheduleViewer(
    modifier: Modifier = Modifier,
    groupSchedule: GroupSchedule,
) {
    CalendarTabs(
        modifier = modifier,
        start = groupSchedule.start,
        end = groupSchedule.end,
        current = groupSchedule.currentTime,
    ) {
        val schedule =
            produceState<GroupLessonsListState>(initialValue = GroupLessonsListState.Loading) {
                val schedule = groupSchedule.getSchedule(it)
                value = GroupLessonsListState.Lessons(schedule)
            }

        LessonsList(state = schedule.value)
    }
}

@Preview(showSystemUi = true)
@Composable
fun GroupScheduleViewerPrev() {
    val data = remember {
        SimpleGroupSchedule(
            groupName = "ФН2-61Б",
            lessons = mapOf(
                // Понедельник
                WeekType.NUMERATOR to DayOfWeek.MONDAY to listOf(
                    GroupLesson(
                        begin = Time(13, 50), end = Time(15, 25),
                        name = "Элективный курс по физической культуре и спорту",
                        type = "сем",
                        cabinet = "Каф"
                    ),
                    GroupLesson(
                        begin = Time(15, 40), end = Time(17, 15),
                        name = "Уравнения математической физики",
                        type = "сем",
                        cabinet = "631л",
                        teacher = "Новожилова О.В."
                    ),
                    GroupLesson(
                        begin = Time(17, 25), end = Time(19, 0),
                        name = "Уравнения математической физики",
                        type = "лек",
                        cabinet = "544л",
                        teacher = "Лычев С.А."
                    )
                ),
                WeekType.DENOMINATOR to DayOfWeek.MONDAY to listOf(
                    GroupLesson(
                        begin = Time(13, 50), end = Time(15, 25),
                        name = "Элективный курс по физической культуре и спорту",
                        type = "сем",
                        cabinet = "Каф"
                    ),
                    GroupLesson(
                        begin = Time(15, 40), end = Time(17, 15),
                        name = "Уравнения математической физики",
                        type = "лек",
                        cabinet = "544л",
                        teacher = "Лычев С.А."
                    ),
                    GroupLesson(
                        begin = Time(17, 25), end = Time(19, 0),
                        name = "Уравнения математической физики",
                        type = "лек",
                        cabinet = "544л",
                        teacher = "Лычев С.А."
                    ),
                ),
                // Вторник
                WeekType.NUMERATOR to DayOfWeek.TUESDAY to listOf(
                    GroupLesson(
                        begin = Time(8, 30), end = Time(10, 5),
                        name = "Элективный курс по физической культуре и спорту",
                        type = "сем",
                        cabinet = "Каф"
                    ),
                    GroupLesson(
                        begin = Time(10, 15), end = Time(11, 50),
                        name = "Иностранный язык",
                        type = "сем",
                        cabinet = "Каф"
                    ),
                    GroupLesson(
                        begin = Time(12, 0), end = Time(13, 35),
                        name = "Математические модели механики сплошной среды",
                        type = "лек",
                        cabinet = "216л",
                        teacher = "Кувыркин Г.Н."
                    ),
                    GroupLesson(
                        begin = Time(13, 50), end = Time(15, 25),
                        name = "Математические модели механики сплошной среды",
                        type = "лек",
                        cabinet = "544л",
                        teacher = "Кувыркин Г.Н."
                    ),
                    GroupLesson(
                        begin = Time(15, 40), end = Time(17, 15),
                        name = "Философия",
                        type = "сем",
                        cabinet = "524л"
                    ),
                ),
                WeekType.DENOMINATOR to DayOfWeek.TUESDAY to listOf(
                    GroupLesson(
                        begin = Time(8, 30), end = Time(10, 5),
                        name = "Элективный курс по физической культуре и спорту",
                        type = "сем",
                        cabinet = "Каф"
                    ),
                    GroupLesson(
                        begin = Time(10, 15), end = Time(11, 50),
                        name = "Иностранный язык",
                        type = "сем",
                        cabinet = "Каф"
                    ),
                    GroupLesson(
                        begin = Time(12, 0), end = Time(13, 35),
                        name = "Математические модели механики сплошной среды",
                        type = "лек",
                        cabinet = "216л",
                        teacher = "Кувыркин Г.Н."
                    ),
                    GroupLesson(
                        begin = Time(13, 50), end = Time(15, 25),
                        name = "Философия",
                        type = "лек",
                        cabinet = "544л",
                    ),
                    GroupLesson(
                        begin = Time(15, 40), end = Time(17, 15),
                        name = "Философия",
                        type = "сем",
                        cabinet = "524л"
                    ),
                ),
                // Среда
                WeekType.NUMERATOR to DayOfWeek.WEDNESDAY to listOf(
                    GroupLesson(
                        begin = Time(8, 30), end = Time(10, 5),
                        name = "ВУЦ",
                        cabinet = "209ю"
                    ),
                    GroupLesson(
                        begin = Time(10, 15), end = Time(11, 50),
                        name = "ВУЦ",
                        cabinet = "209ю"
                    ),
                    GroupLesson(
                        begin = Time(12, 0), end = Time(13, 35),
                        name = "ВУЦ",
                        cabinet = "209ю"
                    ),
                    GroupLesson(
                        begin = Time(13, 30), end = Time(15, 25),
                        name = "ВУЦ",
                        cabinet = "209ю"
                    ),
                ),
                WeekType.DENOMINATOR to DayOfWeek.WEDNESDAY to listOf(
                    GroupLesson(
                        begin = Time(8, 30), end = Time(10, 5),
                        name = "ВУЦ",
                        cabinet = "209ю"
                    ),
                    GroupLesson(
                        begin = Time(10, 15), end = Time(11, 50),
                        name = "ВУЦ",
                        cabinet = "209ю"
                    ),
                    GroupLesson(
                        begin = Time(12, 0), end = Time(13, 35),
                        name = "ВУЦ",
                        cabinet = "209ю"
                    ),
                    GroupLesson(
                        begin = Time(13, 30), end = Time(15, 25),
                        name = "ВУЦ",
                        cabinet = "209ю"
                    ),
                ),
                // Четверг пуст
                // Пятница
                WeekType.NUMERATOR to DayOfWeek.FRIDAY to listOf(
                    GroupLesson(
                        begin = Time(8, 30), end = Time(10, 5),
                        name = "Численные методы решения задач математической физики",
                        type = "лаб",
                        cabinet = "631л"
                    ),
                    GroupLesson(
                        begin = Time(10, 15), end = Time(11, 50),
                        name = "Численные методы решения задач математической физики",
                        type = "лек",
                        cabinet = "544л",
                        teacher = "Лукин В.В."
                    ),
                    GroupLesson(
                        begin = Time(12, 0), end = Time(13, 35),
                        name = "Численные методы решения задач математической физики",
                        type = "сем",
                        cabinet = "524л",
                        teacher = "Сорокин Д.Л."
                    ),
                    GroupLesson(
                        begin = Time(13, 30), end = Time(15, 25),
                        name = "Уравнения математической физики",
                        type = "сем",
                        cabinet = "Каф",
                        teacher = "Новожилова О.В."
                    ),
                ),
                WeekType.DENOMINATOR to DayOfWeek.FRIDAY to listOf(
                    GroupLesson(
                        begin = Time(8, 30), end = Time(10, 5),
                        name = "Численные методы решения задач математической физики",
                        type = "лаб",
                        cabinet = "631л"
                    ),
                    GroupLesson(
                        begin = Time(10, 15), end = Time(11, 50),
                        name = "Численные методы решения задач математической физики",
                        type = "лек",
                        cabinet = "544л",
                        teacher = "Лукин В.В."
                    ),
                    GroupLesson(
                        begin = Time(12, 0), end = Time(13, 35),
                        name = "Численные методы решения задач математической физики",
                        type = "сем",
                        cabinet = "524л",
                        teacher = "Сорокин Д.Л."
                    ),
                    GroupLesson(
                        begin = Time(13, 30), end = Time(15, 25),
                        name = "Уравнения математической физики",
                        type = "сем",
                        cabinet = "Каф",
                        teacher = "Новожилова О.В."
                    ),
                ),
                // Суббота
                WeekType.NUMERATOR to DayOfWeek.SATURDAY to listOf(
                    GroupLesson(
                        begin = Time(10, 15), end = Time(11, 50),
                        name = "Теория вероятностей, математическая статистика, теория случайных процессов",
                        type = "лек",
                        cabinet = "212л",
                        teacher = "Меженная Н. М."
                    ),
                    GroupLesson(
                        begin = Time(12, 0), end = Time(13, 35),
                        name = "Теория вероятностей, математическая статистика, теория случайных процессов",
                        type = "сем",
                        cabinet = "1023л",
                        teacher = "Меженная Н. М."
                    ),
                    GroupLesson(
                        begin = Time(13, 30), end = Time(15, 25),
                        name = "Математические модели механики сплошной среды",
                        type = "сем",
                        cabinet = "525л",
                        teacher = "Савельева И. Ю."
                    ),
                    GroupLesson(
                        begin = Time(15, 40), end = Time(17, 15),
                        name = "Математические модели механики сплошной среды",
                        type = "сем",
                        cabinet = "525л",
                        teacher = "Савельева И. Ю."
                    ),
                ),
                WeekType.DENOMINATOR to DayOfWeek.SATURDAY to listOf(
                    GroupLesson(
                        begin = Time(8, 30), end = Time(10, 5),
                        name = "Теория вероятностей, математическая статистика, теория случайных процессов",
                        type = "лек",
                        cabinet = "212л",
                        teacher = "Меженная Н. М."
                    ),
                    GroupLesson(
                        begin = Time(10, 15), end = Time(11, 50),
                        name = "Теория вероятностей, математическая статистика, теория случайных процессов",
                        type = "лек",
                        cabinet = "212л",
                        teacher = "Меженная Н. М."
                    ),
                    GroupLesson(
                        begin = Time(12, 0), end = Time(13, 35),
                        name = "Теория вероятностей, математическая статистика, теория случайных процессов",
                        type = "сем",
                        cabinet = "1023л",
                        teacher = "Меженная Н. М."
                    ),
                    GroupLesson(
                        begin = Time(13, 30), end = Time(15, 25),
                        name = "Математические модели механики сплошной среды",
                        type = "сем",
                        cabinet = "525л",
                        teacher = "Савельева И. Ю."
                    ),
                ),
            ),
        )
    }

    val start = remember {
        Calendar.getInstance().apply {
            set(Calendar.YEAR, 2022)
            set(Calendar.MONTH, Calendar.SEPTEMBER)
            set(Calendar.DAY_OF_MONTH, 1)
        }
    }

    val end = remember {
        Calendar.getInstance().apply {
            set(Calendar.YEAR, 2022)
            set(Calendar.MONTH, Calendar.DECEMBER)
            set(Calendar.DAY_OF_MONTH, 31)
        }
    }

    val current = remember {
        Calendar.getInstance().apply {
            set(Calendar.YEAR, 2022)
            set(Calendar.MONTH, Calendar.OCTOBER)
            set(Calendar.DAY_OF_MONTH, 14)
            set(Calendar.HOUR_OF_DAY, 14)
            set(Calendar.MINUTE, 0)
        }
    }

    val groupSchedule = GroupScheduleImpl(
        start = start,
        end = end,
        currentTime = current,
        schedule = data,
    )

    BMSTUScheduleTheme(darkTheme = false) {
        GroupScheduleViewer(
            modifier = Modifier.fillMaxSize(),
            groupSchedule = groupSchedule,
        )
    }
}