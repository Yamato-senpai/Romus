package com.example.romus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.romus.ui.theme.RomusTheme
import com.example.views.LoginScreen
import com.example.views.EcraPrincipal
import com.example.views.GameDetail
import com.example.views.GameItem
import com.example.views.WarzoneDetail
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import com.example.views.HistoryItem
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import com.example.romus.controller.UserPrefs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RomusTheme {
                val loggedIn = remember { mutableStateOf(true) }
                val selectedGame = remember { mutableStateOf<GameItem?>(null) }
                val historyItems = remember { mutableStateListOf<HistoryItem>() }
                val userName = remember { mutableStateOf("Utilizador") }
                val userEmail = remember { mutableStateOf("email@exemplo.com") }
                val scope = rememberCoroutineScope()
                LaunchedEffect(Unit) {
                    UserPrefs.flow(this@MainActivity).collect { state ->
                        userName.value = state.name
                        userEmail.value = state.email
                        historyItems.clear()
                        historyItems.addAll(state.history)
                    }
                }
                if (!loggedIn.value) {
                    LoginScreen(onLogin = { loggedIn.value = true })
                } else if (selectedGame.value != null && selectedGame.value!!.title.contains("Fortnite", ignoreCase = true)) {
                    GameDetail(game = selectedGame.value!!, onBack = { selectedGame.value = null }, onRecordPurchase = { item ->
                        historyItems.add(0, item)
                        scope.launch { UserPrefs.setHistory(this@MainActivity, historyItems.toList()) }
                    })
                } else if (selectedGame.value != null && selectedGame.value!!.title.contains("Warzone", ignoreCase = true)) {
                    WarzoneDetail(game = selectedGame.value!!, onBack = { selectedGame.value = null }, onRecordPurchase = { item ->
                        historyItems.add(0, item)
                        scope.launch { UserPrefs.setHistory(this@MainActivity, historyItems.toList()) }
                    })
                } else {
                    EcraPrincipal(
                        onGameClick = { game ->
                            if (game.title.contains("Fortnite", ignoreCase = true) ||
                                game.title.contains("Warzone", ignoreCase = true)) {
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
                                UserPrefs.setProfile(this@MainActivity, name, email)
                                UserPrefs.setHistory(this@MainActivity, historyItems.toList())
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    RomusTheme {
        LoginScreen()
    }
}