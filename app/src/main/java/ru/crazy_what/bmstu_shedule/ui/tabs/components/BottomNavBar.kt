package ru.crazy_what.bmstu_shedule.ui.tabs.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomAppBar
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.crazy_what.bmstu_shedule.ui.HollowStar
import ru.crazy_what.bmstu_shedule.ui.base_components.SquareIcon
import ru.crazy_what.bmstu_shedule.ui.screen.main.MainSections
import ru.crazy_what.bmstu_shedule.ui.tabs.TabsConstants

@Composable
fun MainScreenBottomNavBar(
    navController: NavController,
) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    val onClick: (route: String) -> Unit = { route ->
        navController.navigate(route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }

    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MainBottomNavItem(
                route = TabsConstants.ROUTE_MAIN_TAB,
                currentDestination = currentDestination,
                onClick = onClick,
                icon = Icons.Filled.Home,
            )
            MainBottomNavItem(
                route = TabsConstants.ROUTE_BOOKMARKS_TAB,
                currentDestination = currentDestination,
                onClick = onClick,
                icon = HollowStar,
            )
            MainBottomNavItem(
                route = TabsConstants.ROUTE_SEARCH_TAB,
                currentDestination = currentDestination,
                onClick = onClick,
                icon = Icons.Filled.Search,
            )
            MainBottomNavItem(
                route = TabsConstants.ROUTE_MORE_TAB,
                currentDestination = currentDestination,
                onClick = onClick,
                icon = Icons.Filled.MoreVert,
            )
        }
    }

}

@Composable
fun MainBottomNavItem(
    route: String,
    currentDestination: NavDestination?,
    onClick: (route: String) -> Unit,
    icon: ImageVector,
    contentDescription: String? = null,
) {
    IconButton(onClick = { onClick(route) }) {
        SquareIcon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = if (currentDestination?.hierarchy?.any { it.route == route } == true)
            // TODO убрать хардкод цвета
                MaterialTheme.colors.primary else Color.Gray
        )
    }
}

@Composable
fun MainBottomBar(
    tabs: Array<MainSections>,
    currentRoute: String,
    navigateToRoute: (String) -> Unit,
) {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (tab in tabs) {
                IconButton(onClick = { navigateToRoute(tab.route) }) {
                    SquareIcon(
                        imageVector = tab.icon,
                        // TODO исправить
                        contentDescription = null,
                        tint = if (tab.route == currentRoute)
                        // TODO убрать хардкод цвета
                            MaterialTheme.colors.primary else Color.Gray
                    )
                }
            }
        }
    }

}