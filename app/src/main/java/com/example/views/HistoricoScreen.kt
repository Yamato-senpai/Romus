package com.example.views


import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.romus.MainActivity
import com.example.romus.ui.theme.RomusTheme
import com.example.romus.model.HistoryItem
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoricoScreen(modifier: Modifier = Modifier, items: List<HistoryItem> = sampleHistory()) {

    val context = LocalContext.current
    Scaffold { innerPadding ->
        Column(modifier = modifier.fillMaxSize()
            .padding(innerPadding), verticalArrangement = Arrangement.spacedBy(12.dp)) {

            TopAppBar(
                title = { Text("Historico") },
                navigationIcon = {
                    IconButton(onClick = { val intent = Intent(context, MainActivity::class.java); context.startActivity(intent) }) {
                        Icon(painter = painterResource(id = com.example.romus.R.drawable.ic_arrow_back), contentDescription = null)
                    }
                },


            )
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp))
            {

                items(items) { item ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(10.dp),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth().padding(top = 5.dp)

                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(item.title, fontWeight = FontWeight.SemiBold)
                                Text(item.date, color = Color(0xFF616161))
                            }
                            Column(modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.End) {
                                Text(item.amount, fontWeight = FontWeight.Bold)
                                Spacer(Modifier.height(6.dp))


                            }
                        }
                    }
                }
                item { Spacer(Modifier.height(24.dp)) }
            }
        }
    }

}



private fun sampleHistory(): List<HistoryItem> = listOf(
    HistoryItem("Compra: V-Bucks 1.000", "18 Nov 2025", "€9,99"),
    HistoryItem("Compra: Battle Pass", "10 Nov 2025", "€9,99"),
    HistoryItem("Compra: COD Points 2.400", "02 Nov 2025", "€19,99")
)

@Preview(showBackground = true)
@Composable
fun HistoricoPreview() {
    RomusTheme(darkTheme = false, dynamicColor = false) {
        HistoricoScreen()
    }
}


