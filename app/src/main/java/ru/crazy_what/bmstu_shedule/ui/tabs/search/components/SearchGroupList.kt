package ru.crazy_what.bmstu_shedule.ui.tabs.search.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.crazy_what.bmstu_shedule.domain.model.Group
import ru.crazy_what.bmstu_shedule.domain.model.normalizeGroupName
import ru.crazy_what.bmstu_shedule.ui.base_components.SimpleList

@Composable
fun SearchGroupList(
    groups: List<Group>,
    request: String,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    // TODO лучше вынести поиск в ViewModel
    val normalizeRequest = normalizeGroupName(request)

    val foundGroups = groups.filter { group -> group.name.startsWith(normalizeRequest) }

    // TODO добавить placeholder (если результат пустой)
    SimpleList(
        modifier = modifier,
        items = foundGroups.map { return@map Pair(it.name, it.uuid) },
        onClickItem = { id -> onClick(id) }
    )
}