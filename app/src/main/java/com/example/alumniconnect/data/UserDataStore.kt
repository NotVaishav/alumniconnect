package com.example.alumniconnect.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserDataStore(private val dataStore: DataStore<Preferences>) {
    companion object {
        private val IS_USER_LOGGED_IN = booleanPreferencesKey("is_user_logged_in")
    }

    val getUserStatus: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_USER_LOGGED_IN] ?: true
    }

    suspend fun saveUserStatus(currentState: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_USER_LOGGED_IN] = !currentState
        }
    }
}