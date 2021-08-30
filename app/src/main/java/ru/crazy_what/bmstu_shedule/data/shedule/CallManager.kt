package ru.crazy_what.bmstu_shedule.data.shedule

import ru.crazy_what.bmstu_shedule.data.listToMapPToT
import ru.crazy_what.bmstu_shedule.data.listToMapTToP

// Этот класс отвечает за получение времени звонков по номеру пары изданию и
// номера пары по зданию и времени звонков этой пары
object CallManager {

    // звонки УЛК и ГЗ
    private val mainBuilding = listOf(
        1 to Pair("08:30", "10:05"),
        2 to Pair("10:15", "11:50"),
        3 to Pair("12:00", "13:35"),
        4 to Pair("13:50", "15:25"),
        5 to Pair("15:40", "17:15"),
        6 to Pair("17:25", "19:00"),
        7 to Pair("19:10", "20:45"),
    )

    // звонки спортивного комплекса
    private val sportsComplex = listOf(
        1 to Pair("08:15", "09:45"),
        2 to Pair("10:00", "11:30"),
        3 to Pair("12:20", "13:50"),
        4 to Pair("14:05", "15:35"),
        5 to Pair("15:50", "17:20"),
        6 to Pair("17:30", "19:00"),
    )

    // Измайлова
    private val Izmailovo = listOf(
        1 to Pair("08:00", "09:30"),
        2 to Pair("09:35", "11:05"),
        3 to Pair("11:10", "12:40"),
        4 to Pair("12:45", "14:15"),
        5 to Pair("14:30", "16:00"),
        6 to Pair("16:10", "17:40"),
    )

    private val mainBuildingMapNumToTime = listToMapTToP(mainBuilding)
    private val mainBuildingMapTimeToNum = listToMapPToT(mainBuilding)

    private val sportsComplexMapNumToTime = listToMapTToP(sportsComplex)
    private val sportsComplexMapTimeToNum = listToMapPToT(sportsComplex)

    private val IzmailovoMapNumToTime = listToMapTToP(Izmailovo)
    private val IzmailovoMapTimeToNum = listToMapPToT(Izmailovo)

    fun getTime(building: Building, num: Int): Pair<String, String> = when (building) {
        Building.MainBuilding, Building.ELB, Building.None -> mainBuildingMapNumToTime[num]
        Building.SportsComplex -> sportsComplexMapNumToTime[num]
        Building.Izmailovo -> IzmailovoMapNumToTime[num]
    } ?: error("Некорректный номер пары $num для $building")

    fun getNumPair(building: Building, time: Pair<String, String>): Int = when (building) {
        Building.MainBuilding, Building.ELB, Building.None -> mainBuildingMapTimeToNum[time]
        Building.SportsComplex -> sportsComplexMapTimeToNum[time]
        Building.Izmailovo -> IzmailovoMapTimeToNum[time]
    } ?: error("Некорректное время $time для $building")

    fun getNumPair(building: Building, time: String, delimiter: String = " "): Int {
        val list = time.split(delimiter, limit = 2)
        return getNumPair(building, Pair(list[0], list[1]))
    }
}