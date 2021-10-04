package ru.crazy_what.bmstu_shedule.domain.repository

interface SettingsRepository {

    suspend fun getMainGroup(): String

    suspend fun getBookmarks(): List<String>

}