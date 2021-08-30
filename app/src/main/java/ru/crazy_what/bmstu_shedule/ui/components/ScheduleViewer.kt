package ru.crazy_what.bmstu_shedule.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            for (les in day) {
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        text = "${les.numPair}: ${les.name}, ${les.teacher}, ${les.room}, ${les.typeLesson}"
                    )
                }
            }
        }
    }
}