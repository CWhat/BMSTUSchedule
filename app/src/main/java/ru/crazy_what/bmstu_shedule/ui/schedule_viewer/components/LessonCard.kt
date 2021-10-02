package ru.crazy_what.bmstu_shedule.ui.schedule_viewer.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ru.crazy_what.bmstu_shedule.data.Lesson
import ru.crazy_what.bmstu_shedule.ui.cardCorner
import ru.crazy_what.bmstu_shedule.ui.cardElevation
import ru.crazy_what.bmstu_shedule.ui.cardZIndex
import ru.crazy_what.bmstu_shedule.ui.sidePaddingOfCard
import ru.crazy_what.bmstu_shedule.ui.theme.BMSTUScheduleTheme
import ru.crazy_what.bmstu_shedule.ui.theme.infoStyle
import ru.crazy_what.bmstu_shedule.ui.theme.teacherStyle
import ru.crazy_what.bmstu_shedule.ui.theme.titleStyle

@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
fun LessonCardPrev() {
    BMSTUScheduleTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.padding(sidePaddingOfCard, 4.dp)) {
                LessonCard(fakeData[0], messageFromAbove = "через 10ч 23мин")
            }
            Box(modifier = Modifier.padding(sidePaddingOfCard, 4.dp)) {
                LessonCard(fakeData[1])
            }
            Box(modifier = Modifier.padding(sidePaddingOfCard, 4.dp)) {
                LessonCard(fakeData[2], timeProgress = 0.21F, messageBelow = "осталось 1ч 15мин")
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
fun LessonsListPrev() {
    BMSTUScheduleTheme(darkTheme = false) {
        LessonsList(
            lessons = fakeData,
            messageFromAbove = Pair(0, "через 10ч 23мин"),
            messageBelow = Pair(2, "осталось 1ч 15мин"),
            progress = Pair(2, 0.21F),
        )
    }
}

private val fakeData = listOf(
    Lesson(
        type = "лек",
        timeStart = "13:50",
        timeEnd = "15:25",
        name = "Кратные интегралы и ряды",
        teacher = "Марчевский И.К.",
        room = "212л",
    ),
    Lesson(
        type = "лек",
        timeStart = "13:50",
        timeEnd = "15:25",
        name = "Кратные интегралы и ряды",
        room = "212л"
    ),
    Lesson(
        type = "лек",
        timeStart = "13:50",
        timeEnd = "15:25",
        name = "Кратные интегралы и ряды",
        teacher = "Марчевский И.К.",
        room = "212л",
    )
)


@Composable
fun LessonCard(
    lesson: Lesson,
    timeProgress: Float? = null,
    messageFromAbove: String? = null,
    messageBelow: String? = null,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(cardZIndex),
        shape = RoundedCornerShape(cardCorner),
        elevation = cardElevation
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            if (messageFromAbove != null) {
                Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colors.primary) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = messageFromAbove, style = infoStyle)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 4.dp, end = 16.dp, 0.dp)
            ) {
                Text(
                    text = "${lesson.timeStart}-${lesson.timeEnd}" +
                            (if (lesson.room != null) ", каб.: ${lesson.room}" else "") +
                            if (!lesson.type.isNullOrEmpty()) ", ${lesson.type}" else "",
                    style = infoStyle
                )
            }

            if (timeProgress == null) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 4.dp),
                    thickness = 2.dp,
                    color = Color.Gray
                )
            } else {
                LinearProgressIndicator(
                    progress = timeProgress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 4.dp)
                        .height(2.dp),
                    color = MaterialTheme.colors.primary,
                    backgroundColor = Color.Green
                )
            }

            Text(
                modifier = Modifier.padding(16.dp, 0.dp),
                text = lesson.name,
                style = titleStyle,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            if (lesson.teacher != null) {
                Text(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 0.dp,
                        end = 16.dp,
                        bottom = 4.dp
                    ),
                    style = teacherStyle,
                    text = lesson.teacher
                )
            } else {
                CompositionLocalProvider(LocalContentColor provides Color.Gray) {
                    Text(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            top = 0.dp,
                            end = 16.dp,
                            bottom = 4.dp
                        ),
                        style = teacherStyle,
                        text = "Преподаватель не указан"
                    )
                }
            }

            if (messageBelow != null) {
                Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colors.primary) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = messageBelow, style = infoStyle)
                    }
                }
            }
        }
    }
}

// TODO переработать
// У messageBelow и messageFromAbove первый параметр - это индекс элемента, второй - текст сообщения
// У progress первый параметр - это индекс элемента, второй - значения прогрессбара
@Composable
fun LessonsList(
    lessons: List<Lesson>,
    messageBelow: Pair<Int, String>? = null,
    messageFromAbove: Pair<Int, String>? = null,
    progress: Pair<Int, Float>? = null
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(lessons) { index, lesson ->
            Box(modifier = Modifier.padding(sidePaddingOfCard, 4.dp)) {
                LessonCard(
                    lesson,
                    timeProgress = if (progress != null && index == progress.first) progress.second else null,
                    messageFromAbove = if (messageFromAbove != null && index == messageFromAbove.first) messageFromAbove.second else null,
                    messageBelow = if (messageBelow != null && index == messageBelow.first) messageBelow.second else null,
                )
            }
        }
    }
}