package ru.crazy_what.bmstu_shedule.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import ru.crazy_what.bmstu_shedule.ui.theme.*

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun LessonCard1Prev() {
    BMSTUScheduleTheme {
        LessonCard(
            fakeData[0]
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun LessonCard2Prev() {
    BMSTUScheduleTheme {
        LessonCard(
            fakeData[1]
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun LessonsListPrev() {
    LessonsList(lessons = fakeData)
}

private val fakeData = listOf(
    Lesson(
        type = "лек",
        timeStart = "13:50",
        timeEnd = "15:25",
        name = "Кратные интегралы и ряды",
        teacher = "Марчевский И.К.",
        room = "212л",
        messageFromAbove = "через 10ч 23мин",
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
        timeProgress = 0.21F,
        messageBelow = "осталось 1ч 15мин",
    )
)


@Composable
fun LessonCard(
    lesson: Lesson
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(cardZIndex),
        shape = RoundedCornerShape(cardCorner),
        border = if (lesson.messageFromAbove != null || lesson.messageBelow != null)
            BorderStroke(2.dp, cardColor)
        else null,
        elevation = cardElevation
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            if (lesson.messageFromAbove != null) {
                Surface(modifier = Modifier.fillMaxWidth(), color = cardColor) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = lesson.messageFromAbove, style = infoStyle)
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
                            if (lesson.room != null) ", каб.: ${lesson.room}" else "" +
                                    if (lesson.type != "") ", ${lesson.type}" else "",
                    style = infoStyle
                )
            }

            if (lesson.timeProgress == null) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 4.dp),
                    thickness = 2.dp,
                    color = Color.Gray
                )
            } else {
                LinearProgressIndicator(
                    progress = lesson.timeProgress, modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 4.dp)
                        .height(2.dp), color = cardColor, backgroundColor = Color.Green
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

            if (lesson.messageBelow != null) {
                Surface(modifier = Modifier.fillMaxWidth(), color = cardColor) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = lesson.messageBelow, style = infoStyle)
                    }
                }
            }
        }
    }
}

@Composable
fun LessonsList(lessons: List<Lesson>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(lessons) { lesson ->
            Box(modifier = Modifier.padding(sidePaddingOfCard, 4.dp)) {
                LessonCard(lesson)
            }
        }
    }
}