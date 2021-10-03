package ru.crazy_what.bmstu_shedule.domain.repository

interface SettingsRepository {

    fun getMainGroup(): String

    fun getBookmarks(): List<String>

}