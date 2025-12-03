package com.example.romus.controller

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.romus.model.HistoryItem


@Composable
fun EcraPrincipalPreview() {
    remember { mutableStateOf(true) }
    val historyItems = remember { mutableStateListOf<HistoryItem>() }
    val userName = remember { mutableStateOf("Utilizador") }
    val userEmail = remember { mutableStateOf("email@exemplo.com") }

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

        }
    }

