package com.apprelogio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.apprelogio.ui.AppRelogio
import com.apprelogio.ui.theme.AppRelogioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppRelogioTheme {
                AppRelogio()
            }
        }
    }
}
