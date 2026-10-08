package com.apprelogio.ui.pomodoro

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.apprelogio.R

/** Espaço reservado para o modo Pomodoro (próxima etapa). */
@Composable
fun PomodoroScreen(modifier: Modifier = Modifier) {
    Box(modifier, contentAlignment = Alignment.Center) {
        Text(
            text = stringResource(R.string.pomodoro_coming_soon),
            style = MaterialTheme.typography.titleLarge,
        )
    }
}
