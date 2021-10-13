package ru.crazy_what.bmstu_shedule.ui.tabs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.crazy_what.bmstu_shedule.ui.tabs.bookmarks.addBookmarksTab
import ru.crazy_what.bmstu_shedule.ui.tabs.main.addMainTab
import ru.crazy_what.bmstu_shedule.ui.tabs.more.addMoreTab
import ru.crazy_what.bmstu_shedule.ui.tabs.search.addSearchTab

@ExperimentalPagerApi
fun NavGraphBuilder.createMainScreenNavGraph(
    selectedGroup: (String) -> Unit,
) {
    addMainTab()
    addBookmarksTab(selectedGroup)
    addSearchTab(selectedGroup)
    addMoreTab()
}