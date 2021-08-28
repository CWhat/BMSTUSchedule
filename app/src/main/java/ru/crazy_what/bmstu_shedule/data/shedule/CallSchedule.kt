package ru.crazy_what.bmstu_shedule.data.shedule

// Эти функции возвращают время начала и конца пары, а на вход принимают номер пары.
// Благодаря этим функциям в базе данных можно хранить только здание и номер данных

// УЛК и ГЗ
fun mainBuilding(num: Int): Pair<String, String> = when (num) {
    1 -> Pair("8:30", "10:05")
    2 -> Pair("10:15", "11:50")
    3 -> Pair("12:00", "13:35")
    4 -> Pair("13:50", "15:25")
    5 -> Pair("15:40", "17:15")
    6 -> Pair("17:25", "19:00")
    7 -> Pair("19:10", "20:45")
    else -> error("Некорректный номер пары: $num")
}

// Спортивный комплекс
fun sportsComplex(num: Int): Pair<String, String> = when (num) {
    1 -> Pair("8:15", "9:45")
    2 -> Pair("10:00", "11:30")
    3 -> Pair("12:20", "13:50")
    4 -> Pair("14:05", "15:35")
    5 -> Pair("15:50", "17:20")
    6 -> Pair("17:30", "19:00")
    else -> error("Некорректный номер пары: $num")
}

// Измайлово
fun Izmailovo(num: Int): Pair<String, String> = when (num) {
    1 -> Pair("8:00", "9:30")
    2 -> Pair("9:35", "11:05")
    3 -> Pair("11:10", "12:40")
    4 -> Pair("12:45", "14:15")
    5 -> Pair("14:30", "16:00")
    6 -> Pair("16:10", "17:40")
    else -> error("Некорректный номер пары: $num")
}

enum class Building {
    MainBuilding,
    ELB, // УЛК
    SportsComplex,
    Izmailovo;

    fun getTime(building: Building, num: Int): Pair<String, String> = when (building) {
        MainBuilding, ELB -> mainBuilding(num)
        SportsComplex -> sportsComplex(num)
        Izmailovo -> Izmailovo(num)
    }
}