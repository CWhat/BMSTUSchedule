package ru.crazy_what.bmstu_shedule.ui.tabs.bookmarks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.crazy_what.bmstu_shedule.ui.base_components.ErrorMessage
import ru.crazy_what.bmstu_shedule.ui.base_components.LoadView
import ru.crazy_what.bmstu_shedule.ui.base_components.SimpleBasicTopAppBar
import ru.crazy_what.bmstu_shedule.ui.base_components.SimpleList

@Composable
fun BookmarksTab(
    clickOnGroups: (String) -> Unit,
    viewModel: BookmarksViewModel = viewModel()
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
                    items = state.groups,
                    onClickItem = { _, group -> clickOnGroups(group) }
                )
            }
            is BookmarksState.Error -> {
                ErrorMessage(
                    text = "При загрузке закладок произошла ошибка: ${state.message}", modifier = Modifier
                        .padding(24.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}