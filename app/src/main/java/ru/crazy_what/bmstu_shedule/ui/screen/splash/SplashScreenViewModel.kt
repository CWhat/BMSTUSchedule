package ru.crazy_what.bmstu_shedule.ui.screen.splash

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// TODO этот экран должен скачивать расписание, если оно не скачано, и переводить на экран с выбором основной группы, если она не выбрана

@HiltViewModel
class SplashScreenViewModel @Inject constructor(

): ViewModel() {
}