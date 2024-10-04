package com.fttx.partner.data.source.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "core-fttx-app",
)

fun getDataStoreObject(context: Context): DataStore<Preferences> {
    return context.dataStore
}
