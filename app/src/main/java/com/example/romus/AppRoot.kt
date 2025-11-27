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
import com.example.romus.model.GameItem
import com.example.romus.model.HistoryItem
import com.example.views.LoginScreen
import com.example.romus.controller.UserPrefs
import com.example.romus.controller.GameDetailActivity
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
        } else {
            EcraPrincipal(
                onGameClick = { game ->
                    val activity = ctx as androidx.activity.ComponentActivity
                    activity.startActivity(GameDetailActivity.newIntent(activity, game))
                },
                historyItems = historyItems,
                profileName = userName.value,
                profileEmail = userEmail.value
            )
        }
    }
}
