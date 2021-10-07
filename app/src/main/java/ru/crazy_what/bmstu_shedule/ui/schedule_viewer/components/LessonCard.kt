package ru.crazy_what.bmstu_shedule.ui.schedule_viewer.components

import androidx.compose.foundation.layout.*
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
import ru.crazy_what.bmstu_shedule.date.Time
import ru.crazy_what.bmstu_shedule.domain.model.Lesson
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
        elevation = cardElevation,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            if (messageFromAbove != null) {
                Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colors.primary) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = messageFromAbove,
                            style = infoStyle,
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 4.dp, end = 16.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "${lesson.beginTime}-${lesson.endTime}" +
                            if (lesson.type.isNotEmpty()) ", ${lesson.type}" else "",
                    style = infoStyle,
                )
                // TODO сюда можно добавить иконку Icons.Default.Place
                Text(
                    text = if (lesson.cabinet.isNotEmpty()) "каб.: ${lesson.cabinet}" else "",
                    style = infoStyle,
                )
            }

            // TODO убрать куда-нибудь в константы
            val dividerHeight = 2.dp

            if (timeProgress == null) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 4.dp),
                    thickness = dividerHeight,
                    color = Color.Gray,
                )
            } else {
                LinearProgressIndicator(
                    progress = timeProgress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 4.dp)
                        .height(dividerHeight),
                    color = MaterialTheme.colors.primary,
                    backgroundColor = Color.Green,
                )
            }

            Text(
                modifier = Modifier.padding(16.dp, 0.dp),
                text = lesson.name,
                style = titleStyle,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            val teachers = lesson.teachers.toTeachersLine()
            if (teachers.isNotBlank()) {
                Text(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 0.dp,
                        end = 16.dp,
                        bottom = 4.dp,
                    ),
                    style = teacherStyle,
                    text = teachers,
                )
            } else {
                CompositionLocalProvider(LocalContentColor provides Color.Gray) {
                    Text(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            top = 0.dp,
                            end = 16.dp,
                            bottom = 4.dp,
                        ),
                        style = teacherStyle,
                        text = "Преподаватель не указан",
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

fun List<String>.toTeachersLine(): String {
    var res = if (this.isNotEmpty()) this[0] else ""
    for (i in 1 until this.size)
        res += ", " + this[i]

    return res
}