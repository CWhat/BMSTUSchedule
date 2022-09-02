package ru.crazy_what.bmstu_shedule.common

typealias Group = String

val Group.faculty: String
    get() {
        // Есть кафедра ЮР, так как есть, например, группа ЮР-91
        // это тоже нужно учитывать
        val index = this.indexOfFirst { it.isDigit() || it == '-' }
        return this.substring(0, index)
    }

val Group.chair: String
    get() {
        val index = this.indexOfFirst { it == '-' }
        return this.substring(0, index)
    }

// Приводит к строку к более правильному формату вида ФН2-32Б, вместо, например, фн2 32б
// TODO это явно можно ускорить, но мне пока лень
fun normalizeGroupName(str: String): Group {
    var res = str.trim()
    while (res.contains("  ")) res.replace("  ", " ")
    res = res.replace(" ", "-")
    res = res.uppercase()
    return res
}