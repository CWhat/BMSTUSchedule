package ru.crazy_what.bmstu_shedule.ui.components

import androidx.compose.runtime.Composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import ru.crazy_what.bmstu_shedule.data.shedule.Scheduler

@ExperimentalPagerApi
@Composable
fun ScheduleViewer(scheduler: Scheduler) {
    val pagerState = rememberPagerState(pageCount = scheduler.numberOfStudyDaysInSemester)

    HorizontalPager(state = pagerState) { pageNum ->
        val day = scheduler.studyDay(pageNum + 1)

        LessonsList(lessons = day)
    }
}