package com.example.romus.controller

import android.content.Context
import android.content.SharedPreferences
import com.example.romus.model.HistoryItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


data class UserState(
    val name: String,
    val email: String,
    val history: List<HistoryItem>)


object UserPrefs {
    private const val PREFS_NAME = "user_prefs"
    private const val KEY_NAME = "user_name"
    private const val KEY_EMAIL = "user_email"
    private const val KEY_HISTORY = "user_history"

    private var flowCache: MutableStateFlow<UserState>? = null


    private fun prefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)


    private fun read(context: Context): UserState {
        val p = prefs(context)
        val name = p.getString(KEY_NAME, "Utilizador") ?: "Utilizador"
        val email = p.getString(KEY_EMAIL, "email@exemplo.com") ?: "email@exemplo.com"
        val historyStr = p.getString(KEY_HISTORY, "") ?: ""
        return UserState(name, email, deserializeHistory(historyStr))
    }


    fun flow(context: Context): Flow<UserState> {
        val current = read(context)
        val cached = flowCache ?: MutableStateFlow(current).also { flowCache = it }
        return cached.asStateFlow()
    }




     fun setHistory(context: Context, items: List<HistoryItem>) {
        val str = serializeHistory(items)
        val p = prefs(context)
        p.edit().putString(KEY_HISTORY, str).apply()
        flowCache?.value = read(context)
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

