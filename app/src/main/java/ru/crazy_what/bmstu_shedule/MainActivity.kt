package ru.crazy_what.bmstu_shedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import ru.crazy_what.bmstu_shedule.ui.components.DateCircleLinePrev
import ru.crazy_what.bmstu_shedule.ui.components.DateCirclePrev
import ru.crazy_what.bmstu_shedule.ui.components.LessonsListPrev
import ru.crazy_what.bmstu_shedule.ui.screens.MainScreen
import ru.crazy_what.bmstu_shedule.ui.theme.BMSTUScheduleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}