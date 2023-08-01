package ru.crazy_what.bmstu_shedule.ui.tabs.bookmarks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.crazy_what.bmstu_shedule.ui.base_components.ErrorMessage
import ru.crazy_what.bmstu_shedule.ui.base_components.LoadView
import ru.crazy_what.bmstu_shedule.ui.base_components.SimpleBasicTopAppBar
import ru.crazy_what.bmstu_shedule.ui.base_components.SimpleList
import ru.crazy_what.bmstu_shedule.ui.tabs.TabsConstants
import ru.crazy_what.bmstu_shedule.ui.theme.littleTitleStyle

@Composable
fun BookmarksTab(
    clickOnGroups: (String) -> Unit,
    viewModel: BookmarksViewModel,
) {
    val state = viewModel.state.value

    Column(modifier = Modifier.fillMaxSize()) {
        SimpleBasicTopAppBar(title = "Закладки")

        when (state) {
            is BookmarksState.Loading -> {
                LoadView()
            }

            is BookmarksState.Bookmarks -> {
                SimpleList(
                    modifier = Modifier.fillMaxSize(),
                    items = state.groups.map { return@map Pair(it.name, it.uuid) },
                    onClickItem = { id -> clickOnGroups(id) },
                    placeholder = {
                        Text(
                            text = "Здесь пока пусто",
                            style = littleTitleStyle,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                )
            }

            is BookmarksState.Error -> {
                ErrorMessage(
                    text = "При загрузке закладок произошла ошибка: ${state.message}",
                    modifier = Modifier
                        .padding(24.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

fun NavGraphBuilder.addBookmarksTab(
    clickOnGroups: (String) -> Unit,
) {
    composable(
        route = TabsConstants.ROUTE_BOOKMARKS_TAB
    ) { navBackStackEntry ->
        Column(modifier = Modifier.fillMaxSize()) {
            BookmarksTab(
                clickOnGroups = clickOnGroups,
                viewModel = hiltViewModel(navBackStackEntry)
            )
        }
    }
}