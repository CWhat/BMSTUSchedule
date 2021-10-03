package ru.crazy_what.bmstu_shedule.common

typealias Group = String

val Group.faculty: String
    get() {
        val index = this.indexOfFirst { it.isDigit() || it == '-' }
        return this.substring(0, index)
    }

val Group.chair: String
    get() {
        val index = this.indexOfFirst { it == '-' }
        return this.substring(0, index)
    }