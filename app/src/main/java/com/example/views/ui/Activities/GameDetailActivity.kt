package com.example.views.ui.Activities

import android.os.Bundle
import android.os.Build
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.romus.R
import com.example.romus.model.GameItem
import com.example.romus.model.PurchaseItem
import com.example.romus.controller.GameCatalog
import com.example.views.ui.theme.RomusTheme
import com.example.views.ui.components.AppTopBar
import com.example.views.ui.components.PurchasableItemRow
import com.example.views.ui.components.PurchaseBottomSheetContent

class GameDetailActivity : ComponentActivity() {
    companion object {
        const val EXTRA_GAME = "extra_game"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val game: GameItem? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_GAME, GameItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_GAME)
        }
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
        val selected = remember { mutableStateOf<PurchaseItem?>(null) }
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        rememberCoroutineScope()
        var showSheet by remember { mutableStateOf(false) }
        LocalContext.current

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
                                text = GameCatalog.descriptionFor(game),
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

                        val purchasables: List<PurchaseItem> = GameCatalog.purchaseItemsFor(game)
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            items(purchasables) { item ->
                                PurchasableItemRow(
                                    imageRes = item.imageRes,
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
                                LaunchedEffect(Unit) { sheetState.show() }
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



    @Preview(showBackground = true)
    @Composable
    fun FortniteDetailPreview() {
        RomusTheme {
            FortniteDetail(
                game = GameItem(
                    "Fortnite",
                    R.drawable.fortnite1,
                    R.drawable.fortnite
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
    val selected = remember { mutableStateOf<PurchaseItem?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    rememberCoroutineScope()
    var showSheet by remember { mutableStateOf(false) }
    LocalContext.current
    Scaffold { innerPadding ->
        Column {


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
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
                            text = GameCatalog.descriptionFor(game),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.DarkGray,
                            maxLines = 5,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Text(text = "Itens disponíveis", style = MaterialTheme.typography.titleSmall)

                    val purchasables: List<PurchaseItem> = GameCatalog.purchaseItemsFor(game)
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(purchasables) { item ->
                            PurchasableItemRow(
                                imageRes = item.imageRes,
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
                            LaunchedEffect(Unit) { sheetState.show() }
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


 

@Preview(showBackground = true)
@Composable
fun WarzoneDetailPreview() {
    RomusTheme {
        WarzoneDetail(
            game = GameItem(
                "Call of Duty: Warzone",
                R.drawable.warzone,
                R.drawable.warzone
            ), onBack = { })
    }
}



