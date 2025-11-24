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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.romus.R
import com.example.romus.ui.theme.GradientEnd
import com.example.romus.ui.theme.GradientStart
import androidx.compose.material3.OutlinedTextField
import com.example.romus.ui.theme.RomusTheme

@Composable
fun PerfilScreen(modifier: Modifier = Modifier, name: String = "Utilizador", email: String = "email@exemplo.com", onUpdateProfile: (String, String) -> Unit = { _, _ -> }) {
    val showDialog = remember { mutableStateOf(false) }
    val newName = remember { mutableStateOf(name) }
    val newEmail = remember { mutableStateOf(email) }
    Column(modifier = modifier.fillMaxSize().background(Color.White).padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(10.dp),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(40.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Surface(shape = CircleShape, color = Color.White, shadowElevation = 4.dp) {
                    Image(
                        painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                        contentDescription = null,
                        modifier = Modifier.size(56.dp)
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(name, fontWeight = FontWeight.SemiBold)
                    Text(email, color = Color(0xFF616161))
                }

            }
        }
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(10.dp),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(30.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("7", fontWeight = FontWeight.Bold)
                    Text("Usos", color = Color(0xFF616161))
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("100", fontWeight = FontWeight.Bold)
                    Text("Pontos", color = Color(0xFF616161))
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("€2.08", fontWeight = FontWeight.Bold)
                    Text("Poupado", color = Color(0xFF616161))
                }
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(30.dp),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                Column(modifier = Modifier.padding(25.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Saldo", style = MaterialTheme.typography.titleSmall)
                    Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("€10.00", color = Color(0xFFD32F2F), fontWeight = FontWeight.Bold)
                        Button(onClick = { }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C4DFF)), shape = RoundedCornerShape(50.dp)) {
                            Text("Recarregar", color = Color.White)
                        }
                    }

                }

            }

        }

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(10.dp),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Conquistas Recentes ", style = MaterialTheme.typography.titleSmall)
                Box(modifier = Modifier.size(48.dp).clip(RoundedCornerShape(12.dp)).background(Color(0xFFF2F2F2)))
                Text("Nenhuma conquista ainda")
                Text("Não desista de nada", color = Color(0xFF616161))
            }
        }

        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C4DFF)),
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier.fillMaxWidth()
        ) { Text("logout", color = Color.White) }

        Spacer(Modifier.height(55.dp))
    }

}

@Preview(showBackground = true)
@Composable
fun PerfilPreview() {
    RomusTheme(darkTheme = true, dynamicColor = false) {
        PerfilScreen()
    }
}