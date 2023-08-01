package ru.crazy_what.bmstu_shedule.domain.model

//typealias Group = String

data class Group(
    val name: String,
    val uuid: String,
)

// Приводит строку к более правильному формату вида ФН2-32Б вместо, например, фн2 32б
// TODO это явно можно ускорить, но мне пока лень
// TODO написать тесты
fun normalizeGroupName(str: String): String {
    var res = str.trim()
    while (res.contains("  ")) res.replace("  ", " ")
    res = res.replace(" ", "-")
    res = res.uppercase()
    return res
}