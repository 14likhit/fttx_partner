package com.fttx.partner.data.source.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.gson.Gson
import javax.inject.Inject

/*
  How to use
    val isLoggedIn = use any coroutine block  { appPreferences.getUserLoggedIn() }
    val isProfileCompleted = use any coroutine block { appPreferences.isProfileCompleted() }

    set in suspend function or coroutine
    dataStore.setUserLoggedIn(true)

 */
class DataStorePreferences
@Inject
constructor(
    dataStore: DataStore<Preferences>,
    private var gson: Gson,
) :
    BaseDataStorePreference(dataStore), IDataStorePreferences {
    suspend fun setUserName(firstName: String) {
        setValue(Keys.userName, firstName)
    }

    suspend fun getUserName(): String {
        return getValue(Keys.userName) ?: ""
    }

    suspend fun getCurrentLatitude(): String? {
        return getValue(Keys.latitude)
    }

    suspend fun getCurrentLongitude(): String? {
        return getValue(Keys.longitude)
    }

    suspend fun setCurrentCity(city: String) {
        setValue(Keys.city, city)
    }

    suspend fun storeAccessToken(token: String) {
        setValue(Keys.accessToken, token)
    }

    suspend fun fetchAccessToken(): String? {
        return getValue(Keys.accessToken)
    }

    override suspend fun getLanguage(): String {
        return ""
    }

    override suspend fun getUserId(): Int? {
        return getValue(Keys.userId)
    }

    override suspend fun saveUserId(userId: Int) {
        setValue(Keys.userId, userId)
    }

    override suspend fun getCustomerId(): Int? {
        return getValue(Keys.customerId)
    }

    override suspend fun saveCustomerId(customerId: Int) {
        setValue(Keys.customerId, customerId)
    }

    override suspend fun getAuthToken(): String? {
        return getValue(Keys.authToken)
    }

    override suspend fun saveAuthToken(authToken: String) {
        setValue(Keys.authToken, authToken)
    }

    override suspend fun setUserLoggedIn(isLoggedIn: Boolean) {
        setValue(Keys.isUserLoggedIn, isLoggedIn)
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return getValue(Keys.isUserLoggedIn) ?: false
    }

    override suspend fun clearDataStore() {
        clearData()
    }
}
