package com.fttx.partner.data.source.local.datastore

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences

open class BaseDataStorePreference(dataStore: DataStore<Preferences>) {
    val prefDataStore = dataStore

    suspend inline fun <reified T> setValue(
        key: Preferences.Key<T>,
        value: T,
    ) {
        prefDataStore.edit {
            it[key] = value
        }
    }

    suspend inline fun <reified T> getValue(key: Preferences.Key<T>): T? {
        return prefDataStore.data
            .catch {
                emptyPreferences()
            }
            .map { prefs ->
                prefs[key]
            }.first()
    }

    suspend fun clearData() {
        prefDataStore.edit { it.clear() }
    }
}
