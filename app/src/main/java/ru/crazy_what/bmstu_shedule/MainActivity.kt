package ru.crazy_what.bmstu_shedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.crazy_what.bmstu_shedule.ui.screens.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen()
        }

        /*lifecycleScope.launchWhenResumed {
            val service = SchedulerServiceImpl()
            val groups = service.groups()
            if (groups is ResponseResult.Error) {
                Log.e("MyLog", groups.message)
            } else if (groups is ResponseResult.Success) {
                setContent {
                    TestScreen(groups = groups.data)
                }
            }
        }*/
    }
}