package com.example.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.example.romus.ui.theme.RomusTheme
import com.example.views.ui.components.AppTopBar
import com.example.views.ui.components.PurchasableItemRow
import com.example.views.ui.components.PurchaseBottomSheetContent
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GameDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val game: GameItem? = intent.getParcelableExtra("")
        setContent {
            RomusTheme {
                if (game == null) {
                    finish()
                    return@RomusTheme
                }
                if (game.title.contains("Warzone", ignoreCase = true)) {
                    WarzoneDetail(
                        game = game,
                        onBack = { finish() },
                    )
                } else {
                    FortniteDetail(
                        game = game,
                        onBack = { finish() }
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun FortniteDetail(
        game: GameItem,
        onBack: () -> Unit
    ) {

        val ctx = LocalContext.current
        val selected = remember { mutableStateOf<Purchasable?>(null) }
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        rememberCoroutineScope()
        var showSheet by remember { mutableStateOf(false) }
        val context = LocalContext.current

        Scaffold { innerPadding ->
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {

                    AppTopBar(title = game.title, onBack = onBack)



                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = game.thumbRes),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(16.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = descriptionFor(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.DarkGray,
                                maxLines = 5,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Text(
                            text = "Itens disponíveis",
                            style = MaterialTheme.typography.titleSmall
                        )

                        val purchasables = purchasablesFor()
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            items(purchasables) { item ->
                                PurchasableItemRow(
                                    imageRes = com.example.romus.R.drawable.vbucks,
                                    title = item.title,
                                    subtitle = item.subtitle,
                                    price = item.price,
                                    onBuy = { selected.value = item; showSheet = true }
                                )
                            }
                            item { Spacer(Modifier.height(24.dp)) }
                        }
                        if (showSheet && selected.value != null) {
                            ModalBottomSheet(
                                onDismissRequest = { showSheet = false; selected.value = null },
                                sheetState = sheetState
                            ) {
                                androidx.compose.runtime.LaunchedEffect(Unit) { sheetState.show() }
                                PurchaseBottomSheetContent(
                                    thumbRes = game.thumbRes,
                                    title = selected.value!!.title,
                                    subtitle = selected.value!!.subtitle,
                                    price = selected.value!!.price,
                                    onConfirm = {
                                        Toast.makeText(
                                            ctx,
                                            "Acabou de comprar o item ${selected.value!!.title} por ${selected.value!!.price}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        showSheet = false
                                        selected.value = null
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

    }

    data class Purchasable(val title: String, val subtitle: String, val price: String)

    private fun descriptionFor(): String =
        "Fortnite é um jogo eletrônico multijogador online e uma plataforma de jogos desenvolvida pela Epic Games e lançada em 2017. Está disponível em sete modos distintos"

    private fun purchasablesFor(): List<Purchasable> = listOf(
        Purchasable(
            "V-Bucks 1,000",
            "Compre 1.000 V-bucks do Fortnite, a moeda do jogo que pode ser gasta no Fortnite",
            kwz(9000)
        ),
        Purchasable(
            "V-Bucks 5,000",
            "Compre 5.000 V-bucks do Fortnite, a moeda do jogo que pode ser gasta no Fortnite",
            kwz(38000)
        ),
        Purchasable(
            "V-bucks 13,500",
            "Compre 13.500 V-Bucks do Fortnite, a moeda do jogo que pode ser gasta no Fortnite. Com elas, você pode adquirir novos itens de personalização na Loja de Itens, além de conteúdo adicional como o Passe de Batalha da Temporada atual!",
            kwz(90000)
        ),
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
            FortniteDetail(
                game = GameItem(
                    "Fortnite",
                    com.example.romus.R.drawable.fortnite1,
                    com.example.romus.R.drawable.fortnite
                ),
                onBack = { }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
    fun WarzoneDetail(
        game: GameItem,
        onBack: () -> Unit
    ) {

    val ctx = LocalContext.current
    val selected = remember { mutableStateOf<WarzoneItem?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    rememberCoroutineScope()
    var showSheet by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Scaffold { innerPadding ->
        Column {


            Column(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)) {
                // Cabeçalho com navegação e ações
                AppTopBar(title = game.title, onBack = onBack)



                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = game.thumbRes),
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = "Call of Duty: Warzone é um jogo eletrônico free-to-play do gênero battle royale desenvolvido pela Infinity Ward e Raven Software e publicado pela Activision.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.DarkGray,
                            maxLines = 5,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Text(text = "Itens disponíveis", style = MaterialTheme.typography.titleSmall)

                    val purchasables = listOf(
                        WarzoneItem(
                            "COD Points 1,100",
                            "Moeda para bundles e Battle Pass.",
                            kwz(10000)
                        ),
                        WarzoneItem(
                            "COD Points 4,000",
                            "Pacote ampliado para bundles premium.",
                            kwz(39000)
                        ),
                        WarzoneItem("COD Points 5,000", "Pacote base da Season", kwz(89000))
                    )
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(purchasables) { item ->
                            PurchasableItemRow(
                                imageRes = com.example.romus.R.drawable.cod,
                                title = item.title,
                                subtitle = item.subtitle,
                                price = item.price,
                                onBuy = { selected.value = item; showSheet = true }
                            )
                        }
                        item { Spacer(Modifier.height(24.dp)) }
                    }
                    if (showSheet && selected.value != null) {
                        ModalBottomSheet(
                            onDismissRequest = { showSheet = false; selected.value = null },
                            sheetState = sheetState
                        ) {
                            androidx.compose.runtime.LaunchedEffect(Unit) { sheetState.show() }
                            PurchaseBottomSheetContent(
                                thumbRes = game.thumbRes,
                                title = selected.value!!.title,
                                subtitle = selected.value!!.subtitle,
                                price = selected.value!!.price,
                                onConfirm = {
                                    Toast.makeText(
                                        ctx,
                                        "Acabou de comprar o item ${selected.value!!.title} por ${selected.value!!.price}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    showSheet = false
                                    selected.value = null
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


data class WarzoneItem(val title: String, val subtitle: String, val price: String)


private fun kwz(amount: Int): String {
    val nf = java.text.NumberFormat.getCurrencyInstance(Locale.getDefault())
    nf.currency = java.util.Currency.getInstance("AOA")
    return nf.format(amount)
}

@Preview(showBackground = true)
@Composable
fun WarzoneDetailPreview() {
    RomusTheme {
        WarzoneDetail(
            game = GameItem(
                "Call of Duty: Warzone",
                com.example.romus.R.drawable.warzone,
                com.example.romus.R.drawable.warzone
            ), onBack = { })
    }
}



