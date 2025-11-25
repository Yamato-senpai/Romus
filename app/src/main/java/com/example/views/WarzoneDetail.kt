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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
 
import com.example.romus.ui.theme.GradientEnd
import com.example.romus.ui.theme.GradientStart
import com.example.romus.ui.theme.RomusTheme
import android.widget.Toast
import com.example.views.HistoryItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarzoneDetail(game: GameItem, onBack: () -> Unit, onRecordPurchase: (HistoryItem) -> Unit = {}) {
    val headerGradient = Brush.verticalGradient(listOf(GradientStart, GradientEnd))
    val ctx = LocalContext.current
    val selected = remember { mutableStateOf<WarzoneItem?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showSheet by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Cabeçalho com navegação e ações
        TopAppBar(
            title = { Text(game.title) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(painter = painterResource(id = com.example.romus.R.drawable.ic_arrow_back), contentDescription = null)
                }
            },
            actions = {
                IconButton(onClick = { }) {
                    Icon(painter = painterResource(id = com.example.romus.R.drawable.ic_heart), contentDescription = null)
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
                    text = "Warzone traz combate intenso com modos competitivos e experiências cinematográficas.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(text = "Itens disponíveis", style = MaterialTheme.typography.titleSmall)

            val purchasables = listOf(
                WarzoneItem("COD Points 1,100", "Moeda para bundles e Battle Pass.", kwz(10000)),
                WarzoneItem("COD Points 2,400", "Pacote popular de CP.", kwz(23000)),
                WarzoneItem("COD Points 4,000", "Pacote ampliado para bundles premium.", kwz(40000)),
                WarzoneItem("COD Points 10,000", "Pacote grande para várias temporadas.", kwz(50000)),
                WarzoneItem("Battle Pass", "Passe de batalha da temporada.", kwz(11000))
            )
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
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Text(item.title, fontWeight = FontWeight.SemiBold)
                                }
                                Text(item.subtitle, color = Color(0xFF616161), maxLines = 2, overflow = TextOverflow.Ellipsis)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(item.price, fontWeight = FontWeight.Bold)
                                Spacer(Modifier.height(6.dp))
                                val btnBrush = Brush.horizontalGradient(listOf(GradientStart, GradientEnd))
                                Button(onClick = { selected.value = item; showSheet = true }, ) {
                                    Text("Comprar", color = Color.White)
                                }
                            }
                        }
                    }
                }
                item { Spacer(Modifier.height(24.dp)) }
            }
            if (showSheet && selected.value != null) {
                ModalBottomSheet(
                    onDismissRequest = { showSheet = false; selected.value = null },
                    sheetState = sheetState
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(selected.value!!.title, style = MaterialTheme.typography.titleMedium)
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = game.thumbRes),
                                contentDescription = null,
                                modifier = Modifier.size(60.dp).clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Text(selected.value!!.subtitle, color = Color.DarkGray)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(selected.value!!.price, fontWeight = FontWeight.Bold)
                            Button(onClick = {
                                Toast.makeText(ctx, "Acabou de comprar o item ${selected.value!!.title} por ${selected.value!!.price}", Toast.LENGTH_SHORT).show()
                                onRecordPurchase(
                                    HistoryItem(
                                        title = "Compra: ${selected.value!!.title}",
                                        date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date()),
                                        amount = selected.value!!.price
                                    )
                                )
                                showSheet = false
                                selected.value = null
                            }) { Text("Comprar com 1-clique") }
                        }
                    }
                }
            }
        }
    }
}

data class WarzoneItem(val title: String, val subtitle: String, val price: String)

@Preview(showBackground = true)
@Composable
fun WarzoneDetailPreview() {
    RomusTheme {
        WarzoneDetail(game = GameItem("Call of Duty: Warzone", com.example.romus.R.drawable.warzone, com.example.romus.R.drawable.warzone), onBack = { })
    }
}

private fun kwz(amount: Int): String {
    val nf = java.text.NumberFormat.getCurrencyInstance(Locale.getDefault())
    nf.currency = java.util.Currency.getInstance("AOA")
    return nf.format(amount)
}
