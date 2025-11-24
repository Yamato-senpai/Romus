package com.example.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
 
import androidx.compose.ui.text.style.TextOverflow
import com.example.romus.ui.theme.GradientStart
import com.example.romus.ui.theme.GradientEnd
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.ui.tooling.preview.Preview
import com.example.romus.ui.theme.RomusTheme
import com.example.views.HistoryItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetail(game: GameItem, onBack: () -> Unit, onRecordPurchase: (HistoryItem) -> Unit = {}) {
    val headerGradient = Brush.verticalGradient(listOf(GradientStart, GradientEnd))
    val ctx = LocalContext.current
    val selected = remember { mutableStateOf<Purchasable?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(game.title) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    androidx.compose.material3.Text("←")
                }
            },

            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
        )



        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = game.thumbRes),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp).clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = descriptionFor(game.title),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(text = "Itens disponíveis", style = MaterialTheme.typography.titleSmall)

            val purchasables = purchasablesFor(game.title)
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(purchasables) { item ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(12.dp),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.title, fontWeight = FontWeight.SemiBold)
                                Text(item.subtitle, color = Color(0xFF616161), maxLines = 2, overflow = TextOverflow.Ellipsis)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(item.price, fontWeight = FontWeight.Bold)
                                Spacer(Modifier.height(6.dp))
                                val btnBrush = Brush.horizontalGradient(listOf(GradientStart, GradientEnd))
                                Button(onClick = { selected.value = item },) {
                                    Text("Comprar", color = Color.White)
                                }
                            }
                        }
                    }
                }
                item { Spacer(Modifier.height(24.dp)) }
            }
            if (selected.value != null) {
                AlertDialog(
                    onDismissRequest = { selected.value = null },
                    title = { Text("Confirmar compra") },
                    text = { Text("Comprar ${selected.value!!.title} por ${selected.value!!.price}?") },
                    confirmButton = {
                        TextButton(onClick = {
                            Toast.makeText(ctx, "Compra realizada", Toast.LENGTH_SHORT).show()
                            onRecordPurchase(
                                HistoryItem(
                                    title = "Compra: ${selected.value!!.title}",
                                    date = currentDateFormatted(),
                                    amount = selected.value!!.price
                                )
                            )
                            selected.value = null
                        }) { Text("Confirmar") }
                    },
                    dismissButton = {
                        TextButton(onClick = { selected.value = null }) { Text("Cancelar") }
                    }
                )
            }
        }
    }
}

data class Purchasable(val title: String, val subtitle: String, val price: String)

private fun descriptionFor(title: String): String =
    "Fortnite é um jogo free-to-play com diversos modos e conteúdos épicos. Jogue com amigos e explore ilhas, eventos e muito mais."

private fun purchasablesFor(title: String): List<Purchasable> = listOf(
    Purchasable("V-Bucks 1,000", "Moeda do jogo para compras na loja.", "8,000KWZ"),
    Purchasable("V-Bucks 2,800", "Pacote econômico de V-Bucks.", "20,000KWZ"),
    Purchasable("V-Bucks 5,000", "Pacote intermediário para skins e itens.", "$32,500KWZ"),
    Purchasable("V-Bucks 13,500", "Pacote grande para coleções completas.", "$100,000KWZ"),
    Purchasable("Battle Pass", "Passe de batalha da temporada 5.", "13,550KWZ")
)

private fun currentDateFormatted(): String =
    SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())

@Preview(showBackground = true)
@Composable
fun GameDetailPreview() {
    RomusTheme {
        GameDetail(game = GameItem("Fortnite", com.example.romus.R.drawable.fortnite1, com.example.romus.R.drawable.fortnite), onBack = { })
    }
}
