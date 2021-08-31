package ru.crazy_what.bmstu_shedule.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import ru.crazy_what.bmstu_shedule.data.Date
import ru.crazy_what.bmstu_shedule.data.DateCircleState
import ru.crazy_what.bmstu_shedule.data.mutableListWithCapacity
import ru.crazy_what.bmstu_shedule.data.shedule.Scheduler

@ExperimentalPagerApi
@Composable
fun ScheduleViewer(scheduler: Scheduler) {
    val pagerState = rememberPagerState(pageCount = scheduler.numberOfStudyDaysInSemester)
    // TODO надо выставлять страницу при первом запуске

    Column(modifier = Modifier.fillMaxSize()) {

        // ТЕСТ
        val days = mutableListWithCapacity<Date>(6)
        val week = scheduler.studyWeek(1)
        for (i in 0..5) {
            val dayOfWeek = week[i]
            val day = Date(
                dayOfWeek = "ПН",
                day = dayOfWeek.dayOfMonth,
                state = DateCircleState.NONE
            )
            days.add(day)
        }
        DateCircleLine(title = "Тест", list = days)
        // ТЕСТ

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { pageNum ->
            val day = scheduler.studyDay(pageNum + 1)

            LessonsList(lessons = day)
        }
    }
}