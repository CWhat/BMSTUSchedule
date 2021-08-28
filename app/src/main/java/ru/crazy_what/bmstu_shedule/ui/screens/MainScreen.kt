package ru.crazy_what.bmstu_shedule.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import ru.crazy_what.bmstu_shedule.ui.components.BottomNavBarPrev
import ru.crazy_what.bmstu_shedule.ui.components.DateCircleLinePrev
import ru.crazy_what.bmstu_shedule.ui.components.LessonsListPrev
import ru.crazy_what.bmstu_shedule.ui.components.SearchTopAppBarPrev
import ru.crazy_what.bmstu_shedule.ui.theme.BMSTUScheduleTheme

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun MainScreen() {
    BMSTUScheduleTheme(darkTheme = false) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val scaffoldState = rememberScaffoldState()
            // Create a coroutineScope for the animation
            val coroutineScope = rememberCoroutineScope()

            Scaffold(scaffoldState = scaffoldState,
                topBar = {
                    /*BasicTopAppBarVector(
                        title = "ФН2-32Б",
                        leftIcon = Icons.Filled.ArrowBack,
                        rightIcon = Icons.Outlined.Star
                    )*/
                    SearchTopAppBarPrev()
                },
                bottomBar = {
                    /*BasicBottomAppBar(
                        icons = listOf(
                            Icons.Filled.Notifications,
                            Icons.Filled.Search,
                            Icons.Filled.Home,
                            //Icons.Filled.Star,
                            HollowStar,
                            Icons.Filled.MoreVert
                        )
                    )*/
                    BottomNavBarPrev()
                }) { paddingValues ->
                Column(modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())) {
                    DateCircleLinePrev()
                    LessonsListPrev()
                    //SimpleListPrev2()
                }
            }
        }
    }
}