package ru.crazy_what.bmstu_shedule.ui.tabs.more

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MoreViewModel : ViewModel() {
    private var _numberOfVisits = mutableStateOf(0)

    val numberOfVisits: State<Int> = _numberOfVisits

    fun addVisit() = _numberOfVisits.value++

}