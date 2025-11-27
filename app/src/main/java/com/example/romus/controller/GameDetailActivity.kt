package com.example.romus.controller

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.example.romus.ui.theme.RomusTheme
import com.example.views.FortniteDetail
import com.example.views.WarzoneDetail
import com.example.romus.model.GameItem
import com.example.romus.model.HistoryItem
import kotlinx.coroutines.launch

class GameDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val game: GameItem? = intent.getParcelableExtra(EXTRA_GAME)
        setContent {
            RomusTheme {
                val historyItems = remember { mutableStateListOf<HistoryItem>() }
                val scope = rememberCoroutineScope()
                LaunchedEffect(Unit) {
                    UserPrefs.flow(this@GameDetailActivity).collect { state ->
                        historyItems.clear()
                        historyItems.addAll(state.history)
                    }
                }
                if (game == null) {
                    finish()
                    return@RomusTheme
                }
                if (game.title.contains("Warzone", ignoreCase = true)) {
                    WarzoneDetail(
                        game = game,
                        onBack = { finish() },
                        onRecordPurchase = { item ->
                            historyItems.add(0, item)
                            scope.launch {
                                UserPrefs.setHistory(
                                    this@GameDetailActivity,
                                    historyItems.toList()
                                )
                            }
                        }
                    )
                } else {
                    FortniteDetail(
                        game = game,
                        onBack = { finish() },
                        onRecordPurchase = { item ->
                            historyItems.add(0, item)
                            scope.launch {
                                UserPrefs.setHistory(
                                    this@GameDetailActivity,
                                    historyItems.toList()
                                )
                            }
                        }
                    )
                }
            }
        }
    }

    companion object {
        const val EXTRA_GAME = "extra_game"

        fun newIntent(activity: ComponentActivity, item: GameItem): Intent =
            Intent(activity, GameDetailActivity::class.java).putExtra(EXTRA_GAME, item)
    }
}
