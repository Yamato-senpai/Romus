package com.example.romus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.romus.ui.theme.RomusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RomusTheme { RomusApp() }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    RomusTheme {
        RomusApp()
    }
}
