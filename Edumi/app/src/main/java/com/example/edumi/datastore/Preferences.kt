package com.example.edumi.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Preferences(private val context: Context) {

    companion object {
        private val DARK_MODE = booleanPreferencesKey("dark_mode")
        private val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        private val PRIMARY_COLOR_NAME = stringPreferencesKey("primary_color_name")
    }

    val isDarkMode: Flow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[DARK_MODE] ?: false }

    val isNotificationsEnabled: Flow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[NOTIFICATIONS_ENABLED] ?: false }

    val primaryColorName: Flow<String> = context.dataStore.data
        .map { prefs -> prefs[PRIMARY_COLOR_NAME] ?: "Azul" }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { prefs -> prefs[DARK_MODE] = enabled }
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs -> prefs[NOTIFICATIONS_ENABLED] = enabled }
    }

    suspend fun setPrimaryColorName(name: String) {
        context.dataStore.edit { prefs -> prefs[PRIMARY_COLOR_NAME] = name }
    }
}
