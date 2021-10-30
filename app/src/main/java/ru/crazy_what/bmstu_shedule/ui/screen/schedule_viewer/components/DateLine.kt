package ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import ru.crazy_what.bmstu_shedule.data.schedule.StudyDayInfo
import ru.crazy_what.bmstu_shedule.data.schedule.WeekInfo
import ru.crazy_what.bmstu_shedule.ui.theme.littleTitleStyle

// часть с информацией о недели и датами
@ExperimentalPagerApi
@Composable
fun DateLine(
    weeksState: PagerState,
    lessonsState: PagerState,
    onClick: (StudyDayInfo) -> Unit,
    // TODO возможно, надо тоже заменить на Flow
    getWeekInfo: (numWeek: Int) -> WeekInfo,
    // TODO возможно, надо тоже заменить на Flow
    getDayInfo: (numDay: Int) -> StudyDayInfo,
    isToday: (numDay: Int) -> Boolean,
) {
    // часть с информацией о недели и датами
    HorizontalPager(modifier = Modifier.fillMaxWidth(), state = weeksState) { page ->
        val week = getWeekInfo(page)

        Column(modifier = Modifier.fillMaxWidth()) {

            Text(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .align(Alignment.CenterHorizontally),
                text = week.info(),
                textAlign = TextAlign.Center,
                style = littleTitleStyle
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                for (i in week.rangeOfDays.first until week.rangeOfDays.last) {
                    val dayInfo = getDayInfo(i)

                    Box(modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)) {
                        DateCircle(
                            dayInfo.date,
                            state = when {
                                dayInfo.studyDayNum == lessonsState.currentPage -> DateCircleState.SELECT
                                isToday(dayInfo.studyDayNum) -> DateCircleState.CURRENT
                                else -> DateCircleState.NONE
                            },
                            onClick = { onClick(dayInfo) },
                        )
                    }
                }

            }
        }

    }
}

// TODO перейти на это
/*@ExperimentalPagerApi
@Composable
fun DateLine(
    initWeek: Int,
    countWeek: Int,
    getWeek: (id: Int) -> Flow<DateLineState>,
    setWeek: Flow<Int>,
    onClickDay: (id: Int) -> Unit,
) {
    val weeksState = rememberPagerState(pageCount = countWeek, initialPage = initWeek)

    // TODO Не будет ли это вызывать рекомпозицию?
    LaunchedEffect("setWeek") {
        setWeek.onEach {
            weeksState.animateScrollToPage(it)
        }
    }

    // часть с информацией о недели и датами
    HorizontalPager(modifier = Modifier.fillMaxWidth(), state = weeksState) { page ->
        val lineState = getWeek(page).collectAsState(initial = DateLineState.Loading)

        // TODO обрабатывать ошибки
        if (lineState.value !is DateLineState.Week)
            return@HorizontalPager

        val week = (lineState.value as DateLineState.Week).weekElement

        Column(modifier = Modifier.fillMaxWidth()) {

            Text(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .align(Alignment.CenterHorizontally),
                text = week.title,
                textAlign = TextAlign.Center,
                style = littleTitleStyle
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                for (day in week.days) {
                    Box(modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)) {
                        DateCircle(
                            day.dayOfMonth.toString(),
                            day.dayOfWeek.toShortString(),
                            state = day.state,
                            onClick = { onClickDay(day.id) },
                        )
                    }
                }

            }
        }

    }
}

data class DayElement(
    val dayOfMonth: Int,
    val dayOfWeek: DayOfWeek,
    val state: DateCircleState,
    val id: Int,
)

data class WeekElement(
    val title: String,
    val days: List<DayElement>,
    val id: Int,
)

sealed class DateLineState {
    object Loading : DateLineState()
    data class Week(val weekElement: WeekElement) : DateLineState()
    data class Error(val message: String) : DateLineState()
}*/