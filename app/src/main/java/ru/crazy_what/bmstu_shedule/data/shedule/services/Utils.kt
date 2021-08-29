package ru.crazy_what.bmstu_shedule.data.shedule.services

fun <T> mutableListWithCapacity(capacity: Int): MutableList<T> =
    ArrayList(capacity)