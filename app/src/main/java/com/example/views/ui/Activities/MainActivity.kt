package com.example.views.ui.Activities

import android.os.Bundle
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.romus.R
import com.example.romus.model.GameItem
import com.example.views.ui.theme.RomusTheme
import com.example.views.ui.components.BottomNavBar
import com.example.views.ui.components.GameCard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RomusTheme {
                EcraPrincipal(
                    onGameClick = { game ->
                        val intent = Intent(this@MainActivity, GameDetailActivity::class.java)
                        intent.putExtra(GameDetailActivity.EXTRA_GAME, game)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}


@Composable
fun EcraPrincipal(
    onGameClick: (GameItem) -> Unit = {},

    ) {

    var selectedTab by remember { mutableIntStateOf(0) }
    val games = com.example.romus.controller.GameCatalog.sampleGames()
    LocalContext.current

    Scaffold { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(innerPadding)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(shape = CircleShape, color = Color.White, shadowElevation = 4.dp) {
                        Image(
                            painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                            contentDescription = null,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    Text(text = "Romus", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.weight(1f))
                    Surface(shape = CircleShape, color = Color.White, shadowElevation = 4.dp) {
                        Box(modifier = Modifier.size(36.dp), contentAlignment = Alignment.Center) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_bell),
                                contentDescription = null
                            )
                        }
                    }
                    Surface(shape = CircleShape, color = Color.White, shadowElevation = 4.dp) {
                        Box(modifier = Modifier.size(36.dp), contentAlignment = Alignment.Center) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_more_vert),
                                contentDescription = null
                            )
                        }
                    }

                }


                when (selectedTab) {
                    0 -> {

                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)

                        ) {
                            items(games) { game ->
                                GameCard(
                                    imageRes = game.imageRes,
                                    thumbRes = game.thumbRes,
                                    title = game.title,
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = { onGameClick(game) }
                                )
                            }
                            item { Spacer(Modifier.height(72.dp)) }
                        }
                        BottomNavBar(onSelectDestaques = { selectedTab = 0 })
                    }
                }
            }
        }
    }




}


@Preview(showBackground = true)
@Composable
fun EcraPrincipalPreview() {
    RomusTheme {
        EcraPrincipal()
    }
}

