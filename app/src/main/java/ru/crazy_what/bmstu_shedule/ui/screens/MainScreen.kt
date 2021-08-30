package ru.crazy_what.bmstu_shedule.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import ru.crazy_what.bmstu_shedule.ui.components.*
import ru.crazy_what.bmstu_shedule.ui.theme.BMSTUScheduleTheme

@Preview(showBackground = true, device = Devices.PIXEL, widthDp = 360, heightDp = 640)
@Composable
fun MainScreen() {
    BMSTUScheduleTheme(darkTheme = false) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                /*BasicTopAppBarVector(
                title = "ФН2-32Б",
                leftIcon = Icons.Filled.ArrowBack,
                rightIcon = Icons.Outlined.Star
                )*/
                SearchTopAppBarPrev()

                Column(modifier = Modifier.weight(1F)) {
                    DateCircleLinePrev()
                    LessonsListPrev()
                    //SimpleListPrev2()
                }

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
            }
        }
    }
}

/*@Composable
fun TestScreen(groups: List<String>) {
    BMSTUScheduleTheme(darkTheme = false) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                SearchTopAppBarPrev()

                Column(modifier = Modifier.weight(1F)) {
                    GroupSelectionList(groups = groups, select = { Log.d("MyLog", "Да!!!!!!!!!!") })
                }
                BottomNavBarPrev()
            }
        }
    }
}*/