package ru.crazy_what.bmstu_shedule.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ru.crazy_what.bmstu_shedule.ui.cardCorner
import ru.crazy_what.bmstu_shedule.ui.cardElevation
import ru.crazy_what.bmstu_shedule.ui.cardZIndex
import ru.crazy_what.bmstu_shedule.ui.sidePaddingOfCard
import ru.crazy_what.bmstu_shedule.ui.theme.BMSTUScheduleTheme
import ru.crazy_what.bmstu_shedule.ui.theme.titleStyle

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun RoundedColumnPrev() {
    BMSTUScheduleTheme {
        RoundedColumn(count = 5) { num ->
            Text(
                modifier = Modifier.padding(16.dp, 4.dp),
                text = "Item ${num + 1}",
                style = titleStyle
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
fun SimpleListPrev1() {
    BMSTUScheduleTheme {
        SimpleList(
            items = listOf(fakeData[0]),
            onClick = { _, _ -> })
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
fun SimpleListPrev2() {
    BMSTUScheduleTheme {
        SimpleList(
            items = fakeData,
            onClick = { _, _ -> })
    }
}

private val fakeData =
    listOf("АК", "БМТ", "ИБМ", "ИУ", "Л", "ЛТ", "МТ", "РЛ", "РКТ", "РЛ", "ФН", "Э", "ЮР")

// TODO добавить эффект затемнения принажатии
@Composable
fun RoundedColumn(count: Int, item: @Composable (Int) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(count) { num ->
            val shape = when {
                count == 1 -> RoundedCornerShape(cardCorner)
                num == 0 -> RoundedCornerShape(topStart = cardCorner, topEnd = cardCorner)
                num == count - 1 -> RoundedCornerShape(
                    bottomStart = cardCorner,
                    bottomEnd = cardCorner
                )
                else -> RoundedCornerShape(0.dp)
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = sidePaddingOfCard, vertical = 1.dp)
                    .zIndex(cardZIndex),
                shape = shape,
                elevation = cardElevation,
                backgroundColor = Color.White
            ) {
                item(num)
            }
        }
    }
}

@Composable
fun SimpleList(items: List<String>, onClick: (num: Int, item: String) -> Unit) {
    RoundedColumn(count = items.size) { num ->
        Box(modifier = Modifier.padding(16.dp, 8.dp)) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = items[num],
                style = titleStyle
            )

            IconButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = { onClick(num, items[num]) }) {
                SquareIcon(imageVector = Icons.Filled.ArrowForward, contentDescription = "forward")
            }
        }
    }
}

// TODO добавить список выбора группы
// Эта хрень не работает
/*@Composable
fun GroupSelectionList(groups: List<String>, select: (String) -> Unit) {

    var initialized by remember {
        mutableStateOf(false)
    }

    var state: MutableState<GroupSelectionListState>? = null

    if (!initialized) {

        // кафедры, но я не уверен в переводе
        val chairsToGroups = groups.groupBy { group ->
            val index = group.indexOfFirst { it == '-' }
            group.substring(0, index)
        }

        val facultiesToChairs = groups.groupBy(keySelector = { chair ->
            val index = chair.indexOfFirst { it.isDigit() }
            chair.substring(0, index)
        }, valueTransform = { group ->
            val index = group.indexOfFirst { it == '-' }
            group.substring(0, index)
        })

        for (el in facultiesToChairs.entries)
            Log.d("MyLog", "${el.key}: ${el.value}")

        state = remember {
            mutableStateOf(GroupSelectionListState(facultiesToChairs, chairsToGroups))
        }

        initialized = true
    }

    //val state = GroupSelectionListState(facultiesToChairs, chairsToGroups)

    if (state != null) {
        if (state.value.groups != null) {
            SimpleList(items = state.value.groups!!, onClick = { _, group -> select(group) })
        } else if (state.value.chairs != null) {
            SimpleList(
                items = state.value.chairs!!,
                onClick = { _, chair -> state.value.groups = state.value.chairsToGroups[chair] })
        } else {
            SimpleList(
                items = state.value.faculties,
                onClick = { _, faculty ->
                    Log.d("MyLog", "Нажали на $faculty")
                    state.value.chairs = state.value.facultiesToChairs[faculty]
                })
        }
    }

    // TODO добавить обработчик нажатия кнопки назад

}

data class GroupSelectionListState(
    val facultiesToChairs: Map<String, List<String>>,
    val chairsToGroups: Map<String, List<String>>,
    val faculties: List<String> = facultiesToChairs.keys.toList(),
    var chairs: List<String>? = null,
    var groups: List<String>? = null
)*/