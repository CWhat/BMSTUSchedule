package ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.abs

@ExperimentalPagerApi
@Composable
fun PageTabs(
    modifier: Modifier = Modifier,
    // TODO добавить modifier для отдельных частей
    countElements: Int,
    countElementsInPage: Int = 6,
    initElement: Int = 0,
    tabsPageInfo: @Composable (pageNum: Int) -> Unit,
    // offset:
    // 0.F - это текущая страница
    // (0.F, 1.F) - идет перелистывание с этой страницы или на эту
    // 1.F - эта страница полностью видна
    tabLayout: @Composable (elementId: Int, offset: Float) -> Unit,
    hostLayout: @Composable (elementId: Int) -> Unit,
) {
    val isPage: (pageNum: Int) -> Int = { pageNum ->
        pageNum / countElementsInPage
    }

    val tabsState = rememberPagerState(
        pageCount = (countElements / countElementsInPage) + if (countElements % countElementsInPage != 0) 1 else 0,
        initialPage = isPage(initElement),
    )
    val elementsState = rememberPagerState(pageCount = countElements, initialPage = initElement)
    val coroutineScope = rememberCoroutineScope()
    val currentElementPage =
        remember(elementsState.currentPage) { elementsState.currentPage }
    val currentElementOffset =
        remember(elementsState.currentPageOffset) { elementsState.currentPageOffset }

    // перелистывание при изменении страницы elementState
    LaunchedEffect(elementsState) {
        // Collect from the a snapshotFlow reading the currentPage
        snapshotFlow { elementsState.currentPage }.collect { elementId ->
            tabsState.animateScrollToPage(isPage(elementId))
        }
    }

    Column(modifier = modifier) {
        HorizontalPager(modifier = Modifier.fillMaxWidth(), state = tabsState) { page: Int ->
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
                Box(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .align(Alignment.CenterHorizontally),
                ) {
                    tabsPageInfo(page)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    val startTab = countElementsInPage * page
                    val endTab = startTab + (countElementsInPage - 1)

                    for (tabId in startTab..endTab) {
                        Box(modifier = Modifier
                            .padding(top = 8.dp, bottom = 16.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                            ) {
                                coroutineScope.launch {
                                    elementsState.animateScrollToPage(tabId)
                                }
                            }
                        ) {
                            // TODO будет ли оно меняться?
                            val delta = tabId - currentElementPage

                            // вычисляем offset
                            // TODO правильно ли работает
                            if (delta == 0) {
                                tabLayout(tabId, 1F - abs(currentElementOffset))
                            } else if (delta == 1 && currentElementOffset > 0) {
                                tabLayout(tabId, 1 - currentElementOffset)
                            } else if (delta == -1 && currentElementOffset < 0) {
                                tabLayout(tabId, currentElementOffset - 1)
                            } else {
                                tabLayout(tabId, 0F)
                            }
                        }
                    }
                }
            }
        }

        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F), state = elementsState
        ) { elementId: Int ->
            hostLayout(elementId)
        }
    }
}