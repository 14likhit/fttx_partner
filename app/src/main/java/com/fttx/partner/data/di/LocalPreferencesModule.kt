package com.fttx.partner.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.fttx.partner.data.source.local.datastore.BaseDataStorePreference
import com.fttx.partner.data.source.local.datastore.DataStorePreferences
import com.fttx.partner.data.source.local.datastore.IDataStorePreferences
import com.fttx.partner.data.source.local.datastore.getDataStoreObject
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalPreferencesModule {

    @Provides
    @Singleton
    fun dataStore(
        @ApplicationContext appContext: Context,
    ): DataStore<Preferences> = getDataStoreObject(appContext)

    @Provides
    @Singleton
    fun providesDefaultDataStore(
        dataStore: DataStore<Preferences>,
        gson: Gson,
    ): BaseDataStorePreference {
        return DataStorePreferences(dataStore, gson)
    }

    @Provides
    @Singleton
    fun providesDefaultIDataStore(
        dataStore: DataStore<Preferences>,
        gson: Gson,
    ): IDataStorePreferences {
        return DataStorePreferences(dataStore, gson = gson)
    }

}