package ru.crazy_what.bmstu_shedule.data

fun <T> mutableListWithCapacity(capacity: Int): MutableList<T> =
    ArrayList(capacity)