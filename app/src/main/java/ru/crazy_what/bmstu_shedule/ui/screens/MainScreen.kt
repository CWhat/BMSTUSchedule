package ru.crazy_what.bmstu_shedule.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import ru.crazy_what.bmstu_shedule.MainViewModel
import ru.crazy_what.bmstu_shedule.ui.HollowStar
import ru.crazy_what.bmstu_shedule.ui.components.*
import ru.crazy_what.bmstu_shedule.ui.screens.tabs.MoreStateMachine
import ru.crazy_what.bmstu_shedule.ui.screens.tabs.SearchStateMachine
import ru.crazy_what.bmstu_shedule.ui.theme.BMSTUScheduleTheme

val LocalViewModel = compositionLocalOf<MainViewModel> { error("ViewModel не проброшен") }

@ExperimentalPagerApi
@Preview(showBackground = true, device = Devices.PIXEL, widthDp = 360, heightDp = 640)
@Composable
fun MainScreen() {
    val pagerState = rememberPagerState(pageCount = 4, initialOffscreenLimit = 3)
    val coroutineScope = rememberCoroutineScope()

    val searchStateMachine = SearchStateMachine(LocalViewModel.current)
    val moreStateMachine = MoreStateMachine()

    BMSTUScheduleTheme(darkTheme = false) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {

            Column(modifier = Modifier.fillMaxSize()) {
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F),
                    state = pagerState,
                    dragEnabled = false,
                    verticalAlignment = Alignment.Top
                ) { page ->
                    when (page) {
                        0 -> {
                            Column(modifier = Modifier.fillMaxSize()) {
                                SimpleBasicTopAppBar(title = "ФН2-32Б")

                                DateCircleLinePrev()
                                LessonsListPrev()
                                //SimpleListPrev2()
                            }
                        }
                        1 -> {
                            Column(modifier = Modifier.fillMaxSize()) {
                                SimpleBasicTopAppBar(title = "Избранное")
                                LoadView()
                            }
                        }
                        2 -> {
                            searchStateMachine.buildUI()
                        }
                        3 -> {
                            moreStateMachine.buildUI()
                        }
                    }
                }

                BottomNavBar(
                    icons = listOf(
                        //Icons.Filled.Notifications,
                        Icons.Filled.Home,
                        HollowStar,
                        //Icons.Filled.Star,
                        Icons.Filled.Search,
                        Icons.Filled.MoreVert,
                    ),
                    initialPage = 0
                ) { page ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(page)
                    }
                }

                /*BottomNavigation() {

                }*/

            }
        }
    }
}