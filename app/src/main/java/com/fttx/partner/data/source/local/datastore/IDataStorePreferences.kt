package com.fttx.partner.data.source.local.datastore

interface IDataStorePreferences {
    suspend fun getLanguage(): String
    suspend fun getUserId(): Int?
    suspend fun saveUserId(userId: Int)
    suspend fun getCustomerId(): Int?
    suspend fun saveCustomerId(customerId: Int)
    suspend fun getAuthToken(): String?
    suspend fun saveAuthToken(authToken: String)
    suspend fun setUserLoggedIn(isLoggedIn: Boolean)
    suspend fun isUserLoggedIn(): Boolean
    suspend fun saveUserCheckedIn(isCheckedIn: Boolean)
    suspend fun isUserCheckedIn(): Boolean
    suspend fun saveCheckedInTimeStamp(timeStamp: Long)
    suspend fun getCheckedInTimeStamp(): Long
    suspend fun clearDataStore()
}
