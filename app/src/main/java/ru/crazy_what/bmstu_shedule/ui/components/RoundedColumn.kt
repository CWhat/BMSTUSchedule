package ru.crazy_what.bmstu_shedule.ui.components

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
import androidx.compose.runtime.Composable
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
            onClick = { })
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
fun SimpleListPrev2() {
    BMSTUScheduleTheme {
        SimpleList(
            items = fakeData,
            onClick = { })
    }
}

private val fakeData =
    listOf("АК", "БМТ", "ИБМ", "ИУ", "Л", "ЛТ", "МТ", "РЛ", "РКТ", "РЛ", "ФН", "Э", "ЮР")


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
fun SimpleList(items: List<String>, onClick: (Int) -> Unit) {
    RoundedColumn(count = items.size) { num ->
        Box(modifier = Modifier.padding(16.dp, 8.dp)) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = items[num],
                style = titleStyle
            )

            IconButton(modifier = Modifier.align(Alignment.CenterEnd), onClick = { onClick(num) }) {
                SquareIcon(imageVector = Icons.Filled.ArrowForward, contentDescription = "forward")
            }
        }
    }
}