package ru.crazy_what.bmstu_shedule.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    suspend fun getMainGroupUuid(): Flow<String?>

    suspend fun setMainGroupUuid(uuid: String)

    // TODO реализовать
    //suspend fun getTheme(): Flow<ThemeType>

    // TODO реализовать
    //suspend fun setTheme(theme: ThemeType)

    // возвращает количество посещений по физкультуре
    suspend fun getVisitsOfPe(): Flow<Int>

    suspend fun setVisitsOfPe(visits: Int)

    // TODO добавить incVisitsOfPe и decVisitsOfPe (+1 и -1 соотв)
}