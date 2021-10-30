package ru.crazy_what.bmstu_shedule.ui.screen.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import ru.crazy_what.bmstu_shedule.ui.HollowStar
import ru.crazy_what.bmstu_shedule.ui.tabs.TabsConstants

enum class MainSections(
    val icon: ImageVector,
    val route: String
) {
    MAIN(Icons.Filled.Home, TabsConstants.ROUTE_MAIN_TAB),
    BOOKMARKS(HollowStar, TabsConstants.ROUTE_BOOKMARKS_TAB),
    SEARCH(Icons.Filled.Search, TabsConstants.ROUTE_SEARCH_TAB),
    MORE(Icons.Filled.MoreVert, TabsConstants.ROUTE_MORE_TAB)
}