package ru.crazy_what.bmstu_shedule.ui.schedule_viewer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ScheduleViewerViewModel : ViewModel() {

    private val _groupName = mutableStateOf("ФН2-32Б")
    val groupName: State<String> = _groupName

}