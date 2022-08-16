package ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.crazy_what.bmstu_shedule.ui.theme.BMSTUScheduleTheme
import ru.crazy_what.bmstu_shedule.ui.theme.dateCircleStyle
import kotlin.math.max

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DateCirclePrev() {
    BMSTUScheduleTheme(darkTheme = false) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)) {
                DateCircle(
                    dayOfMonth = "30",
                    dayOfWeek = "ПН",
                    fraction = 0f,
                    isCurrent = false,
                    isNotActive = false,
                )
            }
            Box(modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)) {
                DateCircle(
                    dayOfMonth = "31",
                    dayOfWeek = "ВТ",
                    fraction = 0f,
                    isCurrent = false,
                    isNotActive = false,
                )
            }
            Box(modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)) {
                DateCircle(
                    dayOfMonth = "1",
                    dayOfWeek = "СР",
                    fraction = 0f,
                    isCurrent = true,
                    isNotActive = false,
                )
            }
            Box(modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)) {
                DateCircle(
                    dayOfMonth = "2",
                    dayOfWeek = "ЧТ",
                    fraction = 0f,
                    isCurrent = false,
                    isNotActive = false,
                )
            }
            Box(modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)) {
                DateCircle(
                    dayOfMonth = "3",
                    dayOfWeek = "ПТ",
                    fraction = 1f,
                    isCurrent = false,
                    isNotActive = false,
                )
            }
            Box(modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)) {
                DateCircle(
                    dayOfMonth = "4",
                    dayOfWeek = "СБ",
                    fraction = 0f,
                    isCurrent = false,
                    isNotActive = false,
                )
            }
        }
    }
}

@Composable
fun DateCircle(
    dayOfMonth: String,
    dayOfWeek: String,
    // 1 - текущий выбранный день, 0 - не выбрали
    // промежуточные значения при перелистывании
    fraction: Float,
    isCurrent: Boolean,
    isNotActive: Boolean,
) {
    // Определяем цвета
    val (backgroundColor, borderColor, textColor) = when {
        isNotActive -> Triple(
            Color.Gray,
            Color.Gray,
            MaterialTheme.colors.onSurface
        )
        isCurrent -> Triple(
            lerp(MaterialTheme.colors.surface, MaterialTheme.colors.primary, fraction),
            MaterialTheme.colors.primary,
            lerp(MaterialTheme.colors.primary, MaterialTheme.colors.onPrimary, fraction)
        )
        // перелив между невыбранным и выбранным состоянием
        else -> Triple(
            lerp(MaterialTheme.colors.surface, MaterialTheme.colors.primary, fraction),
            lerp(Color.Gray, MaterialTheme.colors.primary, fraction),
            lerp(MaterialTheme.colors.onSurface, MaterialTheme.colors.onPrimary, fraction)
        )
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
                Text(
                    text = dayOfWeek,
                    textAlign = TextAlign.Center,
                    style = dateCircleStyle
                )
                Text(
                    text = dayOfMonth,
                    textAlign = TextAlign.Center,
                    style = dateCircleStyle
                )
            }
        }
    }
}