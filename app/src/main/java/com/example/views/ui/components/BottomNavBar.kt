package com.example.views.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.romus.R

@Composable
fun BottomNavBar(onSelectDestaques: () -> Unit) {
    NavigationBar(containerColor = androidx.compose.ui.graphics.Color.White) {
        NavigationBarItem(
            selected = true,
            onClick = onSelectDestaques,
            label = { Text("Destaques") },
            icon = { Icon(painter = painterResource(id = R.drawable.ic_star), contentDescription = null) }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            label = { Text("Histórico") },
            icon = { Icon(painter = painterResource(id = R.drawable.ic_clock), contentDescription = null) }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            label = { Text("Perfil") },
            icon = { Icon(painter = painterResource(id = R.drawable.ic_person), contentDescription = null) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    BottomNavBar(onSelectDestaques = {})
}

