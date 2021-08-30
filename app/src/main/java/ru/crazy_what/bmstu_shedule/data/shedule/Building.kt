package ru.crazy_what.bmstu_shedule.data.shedule

// enum class со зданиями
enum class Building {
    MainBuilding,
    ELB, // УЛК
    SportsComplex,
    Izmailovo,
    None
}

fun getBuildingFromRoom(room: String?): Building {
    return when {
        room == null -> Building.None
        room.endsWith("л") -> Building.ELB
        // TODO доделать хотя бы физкультуру, но для нее нужно еще название пары
        else -> Building.MainBuilding
    }
}