package ru.crazy_what.bmstu_shedule.ui.schedule_viewer.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.crazy_what.bmstu_shedule.date.Time
import ru.crazy_what.bmstu_shedule.domain.model.Lesson
import ru.crazy_what.bmstu_shedule.ui.schedule_viewer.LessonWithInfo
import ru.crazy_what.bmstu_shedule.ui.sidePaddingOfCard
import ru.crazy_what.bmstu_shedule.ui.theme.BMSTUScheduleTheme

@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
fun LessonsListPrev() {
    val fakeData = listOf(
        Lesson(
            type = "лек",
            beginTime = Time(13, 50),
            endTime = Time(15, 25),
            name = "Кратные интегралы и ряды",
            teachers = listOf("Марчевский И.К."),
            cabinet = "212л",
            groups = emptyList(),
        ),
        Lesson(
            type = "лек",
            beginTime = Time(13, 50),
            endTime = Time(15, 25),
            name = "Кратные интегралы и ряды",
            teachers = emptyList(),
            cabinet = "212л",
            groups = emptyList(),
        ),
        Lesson(
            type = "лек",
            beginTime = Time(13, 50),
            endTime = Time(15, 25),
            name = "Кратные интегралы и ряды",
            teachers = listOf("Марчевский И.К."),
            cabinet = "212л",
            groups = emptyList(),
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

// TODO переработать
// У messageBelow и messageFromAbove первый параметр - это индекс элемента, второй - текст сообщения
// У progress первый параметр - это индекс элемента, второй - значения прогрессбара
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