package com.example.views

import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.romus.ui.theme.RomusTheme
import com.example.romus.ui.theme.DarkPurpleBackground
import com.example.romus.ui.theme.GradientStart
import com.example.romus.ui.theme.GradientEnd
import com.example.romus.R

@Composable
fun LoginScreen(onLogin: () -> Unit = {}) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val showPassword = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(GradientStart, GradientEnd, DarkPurpleBackground)
                )
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.size(80.dp),
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 6.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                        contentDescription = null
                    )
                }
            }

            Text(text = "Bem-vindo ", color = Color.White)
            Text(text = "Faça login para continuar", color = Color.White.copy(alpha = 0.75f))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 300.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.08f)
                ),
                elevation = CardDefaults.cardElevation(8.dp),
                shape = RoundedCornerShape(30.dp)
            ) {
                Column(
                    modifier = Modifier.padding(30.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        label = { Text("Email") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(30.dp)
                    )

                    OutlinedTextField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        label = { Text("Senha") },
                        singleLine = true,
                        visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {

                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(30.dp)
                    )

                    Text(
                        text = "Esqueci minha senha",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.End).clickable { }
                    )

                    val btnBrush = Brush.horizontalGradient(listOf(GradientStart, GradientEnd))
                    Button(
                        onClick = { onLogin() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .background(btnBrush, RoundedCornerShape(30.dp)),
                        enabled = true
                    ) {
                        Text("Entrar", color = Color.White)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    RomusTheme(darkTheme = true, dynamicColor = false) {
        LoginScreen()
    }
}