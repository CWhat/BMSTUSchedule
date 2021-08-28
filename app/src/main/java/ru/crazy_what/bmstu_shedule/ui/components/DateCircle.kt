package ru.crazy_what.bmstu_shedule.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.crazy_what.bmstu_shedule.data.Date
import ru.crazy_what.bmstu_shedule.data.DateCircleState
import ru.crazy_what.bmstu_shedule.ui.theme.BMSTUScheduleTheme
import ru.crazy_what.bmstu_shedule.ui.theme.cardColor
import ru.crazy_what.bmstu_shedule.ui.theme.dateCircleStyle
import ru.crazy_what.bmstu_shedule.ui.theme.littleTitleStyle
import kotlin.math.max

@Preview(showBackground = true, device = Devices.PIXEL_4, widthDp = 232)
@Composable
fun DateCirclePrev() {
    BMSTUScheduleTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DateCircle(fakeData[2])
            DateCircle(fakeData[3])
            DateCircle(fakeData[4])
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DateCircleLinePrev() {
    BMSTUScheduleTheme {
        DateCircleLine(title = "1 неделя, числитель", list = fakeData)
    }
}

private val fakeData = listOf(
    Date(dayOfWeek = "ПН", day = 30, state = DateCircleState.NONE),
    Date(dayOfWeek = "ВТ", day = 31, state = DateCircleState.NONE),
    Date(dayOfWeek = "СР", day = 1, state = DateCircleState.CURRENT),
    Date(dayOfWeek = "ЧТ", day = 2, state = DateCircleState.NONE),
    Date(dayOfWeek = "ПТ", day = 3, state = DateCircleState.SELECT),
    Date(dayOfWeek = "СБ", day = 4, state = DateCircleState.NONE)
)

@Composable
fun DateCircle(date: Date) {
    // Определяем цвета
    val (backgroundColor, borderColor, textColor) = when (date.state) {
        DateCircleState.NONE -> Triple(Color.White, Color.Gray, Color.Black)
        DateCircleState.CURRENT -> Triple(Color.White, cardColor, cardColor)
        DateCircleState.SELECT -> Triple(cardColor, cardColor, Color.White)
    }

    Box(
        modifier = Modifier
            .border(width = 1.dp, borderColor, shape = CircleShape)
            .background(color = backgroundColor, shape = CircleShape)
            .layout { measurable, constraints ->
                // Measure the composable
                val placeable = measurable.measure(constraints)

                //get the current max dimension to assign width=height
                val currentHeight = placeable.height
                val currentWidth = placeable.width

                val heightCircle = max(currentHeight, currentWidth)

                //assign the dimension and the center position
                layout(heightCircle, heightCircle) {
                    // Where the composable gets placed
                    placeable.placeRelative(
                        (heightCircle - currentWidth) / 2,
                        (heightCircle - currentHeight) / 2
                    )
                }
            }, contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .defaultMinSize(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CompositionLocalProvider(LocalContentColor provides textColor) {
                Text(text = date.dayOfWeek, textAlign = TextAlign.Center, style = dateCircleStyle)
                Text(
                    text = date.day.toString(),
                    textAlign = TextAlign.Center,
                    style = dateCircleStyle
                )
            }
        }
    }
}

@Composable
fun DateCircleLine(title: String, list: List<Date>) {
    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            text = title,
            textAlign = TextAlign.Center,
            style = littleTitleStyle
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            list.forEach { date ->
                Box(modifier = Modifier.padding(8.dp)) {
                    DateCircle(date = date)
                }
            }
        }
    }
}