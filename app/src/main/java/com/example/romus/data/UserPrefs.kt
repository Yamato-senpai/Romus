package com.example.romus.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.views.HistoryItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val ERROR.data: Flow<UserState>
val Context.userDataStore by preferencesDataStore("user_prefs")

data class UserState(val name: String, val email: String, val history: List<HistoryItem>)

object UserPrefs {
    private val KEY_NAME = stringPreferencesKey("user_name")
    private val KEY_EMAIL = stringPreferencesKey("user_email")
    private val KEY_HISTORY = stringPreferencesKey("user_history")

    fun flow(context: Context): Flow<UserState> =
        context.userDataStore.data.map { prefs ->
            val name = prefs[KEY_NAME] ?: "Utilizador"
            val email = prefs[KEY_EMAIL] ?: "email@exemplo.com"
            val historyStr = prefs[KEY_HISTORY] ?: ""
            UserState(name, email, deserializeHistory(historyStr))
        }

    suspend fun setProfile(context: Context, name: String, email: String) {
        context.userDataStore.edit { prefs ->
            prefs[KEY_NAME] = name
            prefs[KEY_EMAIL] = email
        }
    }

    suspend fun setHistory(context: Context, items: List<HistoryItem>) {
        val str = serializeHistory(items)
        context.userDataStore.edit { prefs ->
            prefs[KEY_HISTORY] = str
        }
    }

    private fun serializeHistory(items: List<HistoryItem>): String =
        items.joinToString(separator = "\n") { h ->
            listOf(h.title.replace("\n", " "), h.date.replace("\n", " "), h.amount.replace("\n", " "))
                .joinToString(separator = "|")
        }

    private fun deserializeHistory(raw: String): List<HistoryItem> =
        if (raw.isBlank()) emptyList() else raw.split("\n").mapNotNull { line ->
            val parts = line.split("|")
            if (parts.size < 3) null else HistoryItem(parts[0], parts[1], parts[2])
        }
}

private fun ERROR.edit(function: Any) {}
