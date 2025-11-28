package com.example.views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.romus.model.GameItem
import com.example.romus.model.HistoryItem
import com.example.romus.ui.theme.RomusTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FortniteDetail(game: GameItem, onBack: () -> Unit, onRecordPurchase: (HistoryItem) -> Unit = {}) {

    val ctx = LocalContext.current
    val selected = remember { mutableStateOf<Purchasable?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showSheet by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->
        Column {
            Column(modifier = Modifier.fillMaxSize()
                .padding(innerPadding)) {

                TopAppBar(
                    title = { Text(game.title) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(painter = painterResource(id = com.example.romus.R.drawable.ic_arrow_back), contentDescription = null)
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
                                    modifier = Modifier.padding(20.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = com.example.romus.R.drawable.vbucks),
                                        contentDescription = "",
                                        modifier = Modifier.size(70.dp)
                                    )

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(item.title, fontWeight = FontWeight.SemiBold)
                                        Text(item.subtitle, color = Color(0xFF616161), maxLines = 2, overflow = TextOverflow.Ellipsis)
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(item.price, fontWeight = FontWeight.Bold)
                                        Spacer(Modifier.height(6.dp))

                                        Button(onClick = { selected.value = item; showSheet = true }) {
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
                            androidx.compose.runtime.LaunchedEffect(Unit) { sheetState.show() }
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
                                                date = currentDateFormatted(),
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
    }

}

data class Purchasable(val title: String, val subtitle: String, val price: String)

private fun descriptionFor(title: String): String =
    "Fortnite é um Battle Royale com vários modos como Solo, Duo, Trio, Squad e Criativo. Jogue com amigos e os amasse, SHITT OONN"

private fun purchasablesFor(title: String): List<Purchasable> = listOf(
    Purchasable("V-Bucks 12,500", "Pacote especial", kwz(117000)),
    Purchasable("V-Bucks 5,000", "Pacote intermediário com skin", kwz(5200)),
    Purchasable("V-bucks 10,000", "Pacote base da temporada", kwz(13550)),
)

private fun currentDateFormatted(): String =
    SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())

private fun kwz(amount: Int): String {
    val nf = java.text.NumberFormat.getCurrencyInstance(Locale.getDefault())
    nf.currency = java.util.Currency.getInstance("AOA")
    return nf.format(amount)
}

@Preview(showBackground = true)
@Composable
fun FortniteDetailPreview() {
    RomusTheme {
        FortniteDetail(game = GameItem("Fortnite", com.example.romus.R.drawable.fortnite1, com.example.romus.R.drawable.fortnite), onBack = { })
    }
}
