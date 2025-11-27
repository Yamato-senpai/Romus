package com.example.romus

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import com.example.views.EcraPrincipal
import com.example.views.GameDetail
import com.example.views.GameItem
import com.example.views.HistoryItem
import com.example.views.LoginScreen
import com.example.views.WarzoneDetail
import com.example.romus.controller.UserPrefs
import kotlinx.coroutines.launch

@Composable
fun RomusApp() {
    val loggedIn = remember { mutableStateOf(true) }
    val selectedGame = remember { mutableStateOf<GameItem?>(null) }
    val historyItems = remember { mutableStateListOf<HistoryItem>() }
    val userName = remember { mutableStateOf("Utilizador") }
    val userEmail = remember { mutableStateOf("email@exemplo.com") }
    val scope = rememberCoroutineScope()
    val ctx = LocalContext.current

    LaunchedEffect(Unit) {
        UserPrefs.flow(ctx).collect { state ->
            userName.value = state.name
            userEmail.value = state.email
            historyItems.clear()
            historyItems.addAll(state.history)
        }
    }

    Surface(color = MaterialTheme.colorScheme.background) {
        if (!loggedIn.value) {
            LoginScreen(onLogin = { loggedIn.value = true })
        } else if (selectedGame.value != null && selectedGame.value!!.title.contains("Fortnite", ignoreCase = true)) {
            GameDetail(
                game = selectedGame.value!!,
                onBack = { selectedGame.value = null },
                onRecordPurchase = { item ->
                    historyItems.add(0, item)
                    scope.launch { UserPrefs.setHistory(ctx, historyItems.toList()) }
                }
            )
        } else if (selectedGame.value != null && selectedGame.value!!.title.contains("Warzone", ignoreCase = true)) {
            WarzoneDetail(
                game = selectedGame.value!!,
                onBack = { selectedGame.value = null },
                onRecordPurchase = { item ->
                    historyItems.add(0, item)
                    scope.launch { UserPrefs.setHistory(ctx, historyItems.toList()) }
                }
            )
        } else {
            EcraPrincipal(
                onGameClick = { game ->
                    if (game.title.contains("Fortnite", ignoreCase = true) ||
                        game.title.contains("Warzone", ignoreCase = true)
                    ) {
                        selectedGame.value = game
                    }
                },
                historyItems = historyItems,
                profileName = userName.value,
                profileEmail = userEmail.value,
                onUpdateProfile = { name, email ->
                    userName.value = name
                    userEmail.value = email
                    scope.launch {
                        UserPrefs.setProfile(ctx, name, email)
                        UserPrefs.setHistory(ctx, historyItems.toList())
                    }
                }
            )
        }
    }
}
