package com.example.views


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.romus.R
import com.example.romus.model.GameItem
import com.example.romus.model.HistoryItem
import com.example.romus.ui.theme.RomusTheme

// GameItem movido para com.example.romus.model

@Composable
fun EcraPrincipal(
    onGameClick: (GameItem) -> Unit = {},
    historyItems: List<HistoryItem> = emptyList(),
    profileName: String = "Fabio",
    profileEmail: String = "fabioromulo19@romus.com",

) {

    var selectedTab by remember { mutableIntStateOf(0) }
    val items = listOf(
        GameItem(
            "Fortnite",
            R.drawable.fortnite1,
            R.drawable.fortnite
        ),
        GameItem(
            "Call of Duty: Warzone",
            R.drawable.warzone1,
            R.drawable.warzone
        )
    )
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
                    Spacer(modifier = Modifier.weight(1f)) // empurra ações para a direita
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
                            items(items) { game ->
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    elevation = CardDefaults.cardElevation(10.dp),
                                    shape = RoundedCornerShape(24.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onGameClick(game) }
                                ) {
                                    Column {
                                        Image(
                                            painter = painterResource(id = game.imageRes),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(200.dp)
                                                .clip(
                                                    RoundedCornerShape(
                                                        topStart = 24.dp,
                                                        topEnd = 24.dp
                                                    )
                                                ),
                                            contentScale = ContentScale.Crop
                                        )
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Row(
                                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Surface(
                                                    shape = RoundedCornerShape(8.dp),
                                                    color = Color(0xFFF2F2F2)
                                                ) {
                                                    Image(
                                                        painter = painterResource(id = game.thumbRes),
                                                        contentDescription = null,
                                                        modifier = Modifier.size(30.dp)
                                                    )
                                                }
                                                Text(text = game.title)
                                            }

                                        }
                                    }
                                }
                            }
                            item { Spacer(Modifier.height(72.dp)) }
                        }
                    }

                    1 -> {
                        HistoricoScreen(modifier = Modifier.weight(1f), items = historyItems)
                    }

                    2 -> {
                        PerfilScreen(
                            modifier = Modifier.weight(1f),
                            name = profileName,
                            email = profileEmail
                        )
                    }
                }

                NavigationBar(containerColor = Color.White) {
                    NavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        label = { Text("Destaques") },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_star),
                                contentDescription = null
                            )
                        }
                    )
                    NavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        label = { Text("Histórico") },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_clock),
                                contentDescription = null
                            )
                        }
                    )
                    NavigationBarItem(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        label = { Text("Perfil") },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_person),
                                contentDescription = null
                            )
                        }
                    )
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
