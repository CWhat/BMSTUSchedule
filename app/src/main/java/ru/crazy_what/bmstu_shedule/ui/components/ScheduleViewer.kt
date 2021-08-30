package ru.crazy_what.bmstu_shedule.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import ru.crazy_what.bmstu_shedule.data.shedule.Scheduler

@ExperimentalPagerApi
@Composable
fun ScheduleViewer(scheduler: Scheduler) {
    val pagerState = rememberPagerState(pageCount = scheduler.numberOfStudyDaysInSemester)

    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState,
        verticalAlignment = Alignment.Top
    ) { pageNum ->
        val day = scheduler.studyDay(pageNum + 1)

        LessonsList(lessons = day)
    }
}