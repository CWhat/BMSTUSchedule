package ru.crazy_what.bmstu_shedule.data.shedule

// enum class со зданиями
enum class Building {
    MainBuilding,
    ELB, // УЛК
    SportsComplex,
    Izmailovo,
    None;


    override fun toString(): String = when (this) {
        MainBuilding -> "ГЗ"
        ELB -> "УЛК"
        SportsComplex -> "СК"
        Izmailovo -> "Измайлово"
        None -> ""
    }

}

fun getBuildingFromRoom(room: String?): Building {
    return when {
        room == null -> Building.None
        room.endsWith("л") -> Building.ELB
        // TODO доделать хотя бы физкультуру, но для нее нужно еще название пары
        else -> Building.MainBuilding
    }
}