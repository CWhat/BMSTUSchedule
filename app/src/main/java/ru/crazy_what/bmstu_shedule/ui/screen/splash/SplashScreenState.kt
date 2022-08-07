package ru.crazy_what.bmstu_shedule.ui.screen.splash

sealed class SplashScreenState {
    object Init : SplashScreenState()
    class DownloadInProgress(val current: Int, val total: Int) : SplashScreenState()
    class Error(val message: String) : SplashScreenState()
}
