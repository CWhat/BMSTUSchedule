package ru.crazy_what.bmstu_shedule.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import ru.crazy_what.bmstu_shedule.data.shedule.Scheduler
import ru.crazy_what.bmstu_shedule.ui.theme.littleTitleStyle

// TODO мне кажется, это можно сделать более опрятно
@ExperimentalPagerApi
@Composable
fun ScheduleViewer(scheduler: Scheduler) {
    val coroutineScope = rememberCoroutineScope()

    // TODO убрать "!!", сделать проверки
    val currentDay = scheduler.currentDay!!

    val scheduleState = rememberPagerState(
        pageCount = scheduler.numberOfStudyDaysInSemester,
        initialPage = if (scheduler.currentDay != null) scheduler.currentDay!! - 1 else 0
    )

    val dateState = rememberPagerState(
        pageCount = scheduler.numberOfWeeksInSemester,
        initialPage = if (scheduler.currentDay != null) scheduler.currentWeek!! - 1 else 0
    )

    Column(modifier = Modifier.fillMaxSize()) {

        // текущая отображаемая неделя
        val curVisWeek = scheduler.studyWeekInfo(dateState.currentPage + 1)
        val curVisDay =
            remember(scheduleState.currentPage) {
                val newVal = scheduler.studyDayInfo(scheduleState.currentPage + 1)
                // При изменении страницы расписания надо делать такую проверку,
                // чтобы, если что, прокрутить на нужную страницу строку с датами
                // Хотя логичней было бы использовать currentComposer.changed, но его нельзя
                // просто так использовать. Возможно, есть какой-то способ
                if ((newVal.studyDayNum < curVisWeek.rangeOfDays.first) ||
                    (newVal.studyDayNum >= curVisWeek.rangeOfDays.last)
                ) {
                    coroutineScope.launch {
                        dateState.animateScrollToPage(scheduler.whichWeekDoesDayBelong(newVal) - 1)
                    }
                }
                newVal
            }

        HorizontalPager(modifier = Modifier.fillMaxWidth(), state = dateState) { page ->
            val week = scheduler.studyWeekInfo(page + 1)

            Column(modifier = Modifier.fillMaxWidth()) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
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
                        val dayInfo = scheduler.studyDayInfo(i)

                        Box(modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)) {
                            DateCircle(
                                dayInfo.date,
                                state = when (dayInfo.studyDayNum) {
                                    curVisDay.studyDayNum -> DateCircleState.SELECT
                                    currentDay -> DateCircleState.CURRENT
                                    else -> DateCircleState.NONE
                                },
                                onClick = {
                                    coroutineScope.launch {
                                        scheduleState.animateScrollToPage(dayInfo.studyDayNum - 1)
                                    }
                                }
                            )
                        }
                    }

                }
            }

        }

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = scheduleState,
            verticalAlignment = Alignment.Top
        ) { pageNum ->
            val day = scheduler.studyDay(pageNum + 1)

            LessonsList(lessons = day)
        }
    }
}