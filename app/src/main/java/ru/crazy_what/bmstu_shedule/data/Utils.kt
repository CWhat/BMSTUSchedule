package ru.crazy_what.bmstu_shedule.data

fun <T> mutableListWithCapacity(capacity: Int): MutableList<T> =
    ArrayList(capacity)

fun <T, P> listToMapTToP(list: List<Pair<T, P>>): Map<T, P> = mapOf(*list.toTypedArray())

fun <T, P> listToMapPToT(list: List<Pair<T, P>>): Map<P, T> =
    mapOf(*list.map { Pair(it.second, it.first) }.toTypedArray())