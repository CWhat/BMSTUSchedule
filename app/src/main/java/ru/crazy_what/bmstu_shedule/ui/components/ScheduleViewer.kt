package ru.crazy_what.bmstu_shedule.ui.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import ru.crazy_what.bmstu_shedule.data.Date
import ru.crazy_what.bmstu_shedule.data.DateCircleState
import ru.crazy_what.bmstu_shedule.data.shedule.Scheduler
import ru.crazy_what.bmstu_shedule.data.shedule.toTranslateString
import ru.crazy_what.bmstu_shedule.data.toShortString
import ru.crazy_what.bmstu_shedule.ui.theme.littleTitleStyle

@ExperimentalPagerApi
@Composable
fun ScheduleViewer(scheduler: Scheduler) {
    val coroutineScope = rememberCoroutineScope()

    // TODO убрать "!!"
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

        val curVisWeek = scheduler.studyWeekInfo(dateState.currentPage + 1)
        val curVisDay = scheduler.studyDayInfo(scheduleState.currentPage + 1)
        Log.d("MyLog", "$curVisDay")

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            text = "${curVisWeek.number} неделя, ${curVisWeek.type.toTranslateString()}",
            textAlign = TextAlign.Center,
            style = littleTitleStyle
        )

        if (curVisDay.studyDayNum !in curVisWeek.rangeOfDays) {
            coroutineScope.launch {
                dateState.animateScrollToPage(scheduler.whichWeekDoesDayBelong(curVisDay) - 1)
            }
        }

        // TODO добавить HorizontalPager
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (i in curVisWeek.rangeOfDays.first until curVisWeek.rangeOfDays.last) {
                val dayInfo = scheduler.studyDayInfo(i)
                Box(modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)) {
                    DateCircle(
                        Date(
                            dayOfWeek = dayInfo.dayOfWeek.toShortString(),
                            day = dayInfo.dayOfMonth,
                            state = when (dayInfo.studyDayNum) {
                                curVisDay.studyDayNum -> DateCircleState.SELECT
                                currentDay -> DateCircleState.CURRENT
                                else -> DateCircleState.NONE
                            }
                        )
                    )
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