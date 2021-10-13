package ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import ru.crazy_what.bmstu_shedule.data.schedule.WeekType
import ru.crazy_what.bmstu_shedule.date.DayOfWeek
import ru.crazy_what.bmstu_shedule.date.Time
import ru.crazy_what.bmstu_shedule.domain.model.GroupLesson
import ru.crazy_what.bmstu_shedule.domain.model.LessonInfo
import ru.crazy_what.bmstu_shedule.ui.base_components.ErrorMessage
import ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.model.LessonWithInfo
import ru.crazy_what.bmstu_shedule.ui.sidePaddingOfCard
import ru.crazy_what.bmstu_shedule.ui.theme.BMSTUScheduleTheme

@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
fun LessonsListPrev() {
    val fakeData = listOf(
        GroupLesson(
            info = LessonInfo(
                weekType = WeekType.NUMERATOR,
                dayOfWeek = DayOfWeek.MONDAY,
                type = "лек",
                beginTime = Time(13, 50),
                endTime = Time(15, 25),
                name = "Кратные интегралы и ряды",
                cabinet = "212л",
            ),
            teachers = listOf("Марчевский И.К."),
        ),
        GroupLesson(
            info = LessonInfo(
                weekType = WeekType.NUMERATOR,
                dayOfWeek = DayOfWeek.MONDAY,
                type = "лек",
                beginTime = Time(13, 50),
                endTime = Time(15, 25),
                name = "Кратные интегралы и ряды",
                cabinet = "212л",
            ),
            teachers = emptyList(),
        ),
        GroupLesson(
            info = LessonInfo(
                weekType = WeekType.NUMERATOR,
                dayOfWeek = DayOfWeek.MONDAY,
                type = "лек",
                beginTime = Time(13, 50),
                endTime = Time(15, 25),
                name = "Кратные интегралы и ряды",
                cabinet = "212л"
            ),
            teachers = listOf("Марчевский И.К."),
        )
    )

    BMSTUScheduleTheme(darkTheme = false) {
        LessonsList(
            lessonsWithInfo = fakeData.mapIndexed { i, lesson ->
                LessonWithInfo(
                    lesson = lesson,
                    messageFromAbove = if (i == 0) "через 10ч 23мин" else null,
                    timeProgress = if (i == 2) 0.21F else null,
                    messageBelow = if (i == 2) "осталось 1ч 15мин" else null,
                )
            },
        )
    }
}

@Composable
fun LessonsList(
    lessonsWithInfo: List<LessonWithInfo>,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(lessonsWithInfo) { lessonWithInfo ->
            Box(modifier = Modifier.padding(sidePaddingOfCard, 4.dp)) {
                with(lessonWithInfo) {
                    LessonCard(
                        lesson,
                        timeProgress = timeProgress,
                        messageFromAbove = messageFromAbove,
                        messageBelow = messageBelow,
                    )
                }
            }
        }
    }
}

// TODO в чем отличие StateFlow от Flow?
@Composable
fun LessonsList(lessonsListState: Flow<LessonsListState>) {

    when (val state = lessonsListState.buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
        .collectAsState(initial = LessonsListState.Loading).value) {
        is LessonsListState.Loading -> {
            // TODO если его оставить, то он может показываться на мгновение, что выглядит не очень
            // с этим надо что-то делать
            //CircularProgressIndicator()
        }
        is LessonsListState.Error -> ErrorMessage(text = state.message)
        is LessonsListState.Lessons -> {
            LessonsList(lessonsWithInfo = state.lessonsWithInfo.sortedBy {
                val beginTime = it.lesson.info.beginTime
                return@sortedBy beginTime.hours * 60 + beginTime.minutes
            })
        }
    }

}