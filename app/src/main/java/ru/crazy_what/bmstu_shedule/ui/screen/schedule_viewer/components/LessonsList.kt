package ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components

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
import ru.crazy_what.bmstu_shedule.domain.model.GroupLessonWithInfo
import ru.crazy_what.bmstu_shedule.domain.model.GroupLesson
import ru.crazy_what.bmstu_shedule.ui.base_components.ErrorMessage
import ru.crazy_what.bmstu_shedule.ui.sidePaddingOfCard
import ru.crazy_what.bmstu_shedule.ui.theme.BMSTUScheduleTheme

@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
fun LessonsListPrev() {
    val fakeData = listOf(
        GroupLessonWithInfo(
            groupLesson = GroupLesson(
                type = "лек",
                begin = Time(13, 50),
                end = Time(15, 25),
                name = "Кратные интегралы и ряды",
                cabinet = "212л",
                teacher = "Марчевский И.К.",
            ),
            messageFromAbove = "через 10ч 23мин",
        ),
        GroupLessonWithInfo(
            groupLesson = GroupLesson(
                type = "лек",
                begin = Time(13, 50),
                end = Time(15, 25),
                name = "Кратные интегралы и ряды",
                cabinet = "212л",
            ),
        ),
        GroupLessonWithInfo(
            groupLesson = GroupLesson(
                type = "лек",
                begin = Time(13, 50),
                end = Time(15, 25),
                name = "Кратные интегралы и ряды",
                cabinet = "212л",
                teacher = "Марчевский И.К.",
            ),
            timeProgress = 0.21F,
            messageBelow = "осталось 1ч 15мин",
        )
    )

    BMSTUScheduleTheme(darkTheme = false) {
        LessonsList(fakeData)
    }
}

@Composable
fun LessonsList(
    lessonsWithInfo: List<GroupLessonWithInfo>,
) {
    // TODO можно показывать картинку, если список пустой
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(lessonsWithInfo) { lessonWithInfo ->
            Box(modifier = Modifier.padding(sidePaddingOfCard, 4.dp)) {
                LessonCard(lessonWithInfo)
            }
        }
    }
}

@Composable
fun LessonsList(
    state: GroupLessonsListState,
) {
    when (state) {
        is GroupLessonsListState.Loading -> {
            // TODO если его оставить, то он может показываться на мгновение, что выглядит не очень
            // с этим надо что-то делать
            //CircularProgressIndicator()
        }
        is GroupLessonsListState.Error -> ErrorMessage(text = state.message)
        is GroupLessonsListState.Lessons -> {
            LessonsList(lessonsWithInfo = state.lessonsWithInfo.sortedBy {
                val beginTime = it.begin
                return@sortedBy beginTime.hours * 60 + beginTime.minutes
            })
        }
    }
}