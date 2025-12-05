package com.example.views.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.romus.R
import com.example.romus.ui.theme.RomusTheme

@Composable
fun PurchaseBottomSheetContent(
    thumbRes: Int,
    title: String,
    subtitle: String,
    price: String,
    onConfirm: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(title, style = MaterialTheme.typography.titleMedium)
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = thumbRes),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Text(subtitle, color = Color.DarkGray)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(price, fontWeight = FontWeight.Bold)
            Button(onClick = onConfirm) { Text("Confirmar compra") }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PurchaseBottomSheetContentPreview() {
    RomusTheme {
        PurchaseBottomSheetContent(
            thumbRes = R.drawable.vbucks,
            title = "V-Bucks 1,000",
            subtitle = "Moeda do jogo",
            price = "Kz 9.000",
            onConfirm = {}
        )
    }
}
