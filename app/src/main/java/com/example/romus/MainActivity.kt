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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RomusTheme {
                val loggedIn = remember { mutableStateOf(false) }
                if (loggedIn.value) {
                    EcraPrincipal()
                } else {
                    LoginScreen(onLogin = { loggedIn.value = true })
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