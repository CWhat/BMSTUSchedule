package ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import ru.crazy_what.bmstu_shedule.data.schedule.WeekType
import ru.crazy_what.bmstu_shedule.ui.theme.littleTitleStyle
import java.util.*
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class, FlowPreview::class)
@Composable
fun CalendarTabs(
    modifier: Modifier = Modifier,
    start: Calendar,
    end: Calendar,
    current: Calendar,
    content: @Composable (date: Calendar) -> Unit,
) {
    val helper = remember { DateHelper(start, end, current) }

    val weeksState = rememberPagerState(
        initialPage = helper.currentNumWeek,
    )
    val daysState = rememberPagerState(
        initialPage = helper.currentNumDay,
    )

    val coroutineScope = rememberCoroutineScope()

    // перелистывание при изменении страницы дня
    LaunchedEffect(daysState) {
        // Collect from the a snapshotFlow reading the currentPage
        snapshotFlow { daysState.currentPage }.debounce(200)
            .collect { dayNum ->
                Log.d("MyLog", "isScrollInProgress = ${daysState.isScrollInProgress}")
                val weekNum = helper.weekNum(dayNum)
                //if (weeksState.currentPage != weekNum && !daysState.isScrollInProgress) {
                if (weeksState.currentPage != weekNum) {
                    Log.d("MyLog", "current page = ${weeksState.currentPage}, new page = $weekNum")
                    coroutineScope.launch {
                        weeksState.animateScrollToPage(weekNum)
                    }
                }
            }

    }

    // Это тоже работает, но ощущается более тормознутым. Зато не использует экспериментальное API
    /*LaunchedEffect(daysState) {
        snapshotFlow { daysState.isScrollInProgress }.collect { isScrollInProgress ->
            if (isScrollInProgress) return@collect

            val dayNum = daysState.currentPage
            val weekNum = helper.weekNum(dayNum)
            if (weeksState.currentPage != weekNum) {
                weeksState.animateScrollToPage(weekNum)
            }
        }
    }*/

    Column(modifier = modifier) {
        HorizontalPager(
            count = helper.weeksCount,
            state = weeksState,
        ) { weekNum ->
            val weekInfo = remember { helper.weekInfo(weekNum) }

            Column(modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center) {
                Box(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text(
                        text = "${weekInfo.weekNum + 1} неделя, ${if (weekInfo.type == WeekType.NUMERATOR) "числитель" else "знаменатель"} ",
                        textAlign = TextAlign.Center,
                        style = littleTitleStyle,
                        color = MaterialTheme.colors.onSurface,
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    for (day in weekInfo.days) {
                        var dayModifier = Modifier.padding(top = 8.dp, bottom = 16.dp)

                        if (!day.isNotActive)
                            dayModifier = dayModifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                            ) {
                                coroutineScope.launch {
                                    daysState.animateScrollToPage(day.dayNum)
                                }
                            }

                        Box(modifier = dayModifier) {
                            val fraction = 1F -
                                    ((daysState.currentPage - day.dayNum) + daysState.currentPageOffset)
                                        .coerceIn(-1F, 1F).absoluteValue

                            DateCircle(
                                dayOfMonth = day.dayOfMonth.toString(),
                                dayOfWeek = day.dayOfWeek.toShortString(),
                                isNotActive = day.isNotActive,
                                isCurrent = day.isCurrent,
                                fraction = fraction,
                            )
                        }
                    }

                }
            }

        }

        HorizontalPager(
            count = helper.daysCount,
            state = daysState,
        ) { dayNum ->
            val date = remember { helper.dayNumToDate(dayNum) }
            content(date)
        }
    }
}