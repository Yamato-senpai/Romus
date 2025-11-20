package com.example.views

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.romus.ui.theme.GradientEnd
import com.example.romus.ui.theme.GradientStart
import com.example.romus.ui.theme.RomusTheme

data class HistoryItem(val title: String, val date: String, val amount: String)

@Composable
fun HistoricoScreen(modifier: Modifier = Modifier, items: List<HistoryItem> = sampleHistory()) {
    Column(modifier = modifier.fillMaxSize().padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(text = "Atividades recentes", style = MaterialTheme.typography.titleSmall)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(items) { item ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(10.dp),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(item.title, fontWeight = FontWeight.SemiBold)
                            Text(item.date, color = Color(0xFF616161))
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(item.amount, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(6.dp))
                            val brush = Brush.horizontalGradient(listOf(GradientStart, GradientEnd))
                            Box(
                                modifier = Modifier
                                    .width(56.dp)
                                    .height(6.dp)
                                    .background(brush, RoundedCornerShape(3.dp))
                            )
                        }
                    }
                }
            }
            item { Spacer(Modifier.height(24.dp)) }
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
    RomusTheme(darkTheme = true, dynamicColor = false) {
        HistoricoScreen()
    }
}
