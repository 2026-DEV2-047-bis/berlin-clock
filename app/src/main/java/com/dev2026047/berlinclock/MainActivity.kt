package com.dev2026047.berlinclock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.dev2026047.berlinclock.ui.theme.BerlinClockTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BerlinClockTheme {
                Scaffold { innerPadding ->
                    Text(
                        text = "Berlin Clock",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
