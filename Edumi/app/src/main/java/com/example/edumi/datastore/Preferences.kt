package com.example.edumi.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Preferences(private val context: Context) {

    companion object {
        private val DARK_MODE = booleanPreferencesKey("dark_mode")
        private val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
    }

    val isDarkMode: Flow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[DARK_MODE] ?: false }

    val isNotificationsEnabled: Flow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[NOTIFICATIONS_ENABLED] ?: false }


    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DARK_MODE] = enabled
        }
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs -> prefs[NOTIFICATIONS_ENABLED] = enabled }
    }
}