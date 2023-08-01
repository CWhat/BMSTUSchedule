package ru.crazy_what.bmstu_shedule.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.crazy_what.bmstu_shedule.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val context: Context,
) : SettingsRepository {

    companion object {
        const val FileName = "settings"

        //const val ThemeKey = "theme"
        const val MainGroupUuidKey = "main"
        const val VisitsKey = "visits"
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = FileName)
    private val visits = intPreferencesKey(VisitsKey)
    private val mainGroup = stringPreferencesKey(MainGroupUuidKey)
    //private val theme = intPreferencesKey(ThemeKey)

    override suspend fun getMainGroupUuid(): Flow<String?> =
        context.dataStore.data.map { settings -> settings[mainGroup] }

    override suspend fun setMainGroupUuid(uuid: String) {
        context.dataStore.edit { settings ->
            settings[this.mainGroup] = uuid
        }
    }

    override suspend fun getVisitsOfPe(): Flow<Int> =
        context.dataStore.data.map { settings -> settings[visits] ?: 0 }

    override suspend fun setVisitsOfPe(visits: Int) {
        context.dataStore.edit { settings ->
            settings[this.visits] = visits
        }
    }
}