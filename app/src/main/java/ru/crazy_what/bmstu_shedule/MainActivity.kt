package ru.crazy_what.bmstu_shedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.crazy_what.bmstu_shedule.data.shedule.services.SchedulerServiceImpl
import ru.crazy_what.bmstu_shedule.ui.screens.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.Main + Job()).launch {
            val service = SchedulerServiceImpl()
            service.schedule("ФН2-32Б")
        }

        setContent {
            MainScreen()
        }
    }
}