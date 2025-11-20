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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RomusTheme {
                val loggedIn = remember { mutableStateOf(true) }
                val selectedGame = remember { mutableStateOf<GameItem?>(null) }
                if (!loggedIn.value) {
                    LoginScreen(onLogin = { loggedIn.value = true })
                } else if (selectedGame.value != null && selectedGame.value!!.title.contains("Fortnite", ignoreCase = true)) {
                    GameDetail(game = selectedGame.value!!, onBack = { selectedGame.value = null })
                } else if (selectedGame.value != null && selectedGame.value!!.title.contains("Warzone", ignoreCase = true)) {
                    WarzoneDetail(game = selectedGame.value!!, onBack = { selectedGame.value = null })
                } else {
                    EcraPrincipal(onGameClick = { game ->
                        if (game.title.contains("Fortnite", ignoreCase = true) ||
                            game.title.contains("Warzone", ignoreCase = true)) {
                            selectedGame.value = game
                        }
                    })
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