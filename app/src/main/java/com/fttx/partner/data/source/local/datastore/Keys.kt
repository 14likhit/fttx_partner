package com.fttx.partner.data.source.local.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

internal object Keys {
    val userId = intPreferencesKey("user_id")
    val customerId = intPreferencesKey("customer_id")
    val authToken = stringPreferencesKey("auth_token")
    val isUserLoggedIn = booleanPreferencesKey("is_logged_in")
    val userName = stringPreferencesKey("user_first_name")
    val latitude = stringPreferencesKey("latitude")
    val longitude = stringPreferencesKey("longitude")
    val city = stringPreferencesKey("city")
    val accessToken = stringPreferencesKey("access_token")
}
