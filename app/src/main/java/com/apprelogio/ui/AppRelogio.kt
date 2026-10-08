package com.apprelogio.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.apprelogio.ui.clock.ClockScreen
import com.apprelogio.ui.pomodoro.PomodoroScreen
import com.apprelogio.ui.system.ImmersiveMode

/**
 * Raiz do app. Em retrato mostra a barra de navegação entre os modos;
 * em paisagem o modo atual ocupa a tela inteira (modo imersivo).
 */
@Composable
fun AppRelogio() {
    var mode by rememberSaveable { mutableStateOf(AppMode.Clock) }
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    ImmersiveMode(enabled = isLandscape)

    if (isLandscape) {
        ModeContent(mode, fullScreen = true, modifier = Modifier.fillMaxSize())
    } else {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    AppMode.entries.forEach { item ->
                        NavigationBarItem(
                            selected = item == mode,
                            onClick = { mode = item },
                            icon = { Icon(painterResource(item.icon), contentDescription = null) },
                            label = { Text(stringResource(item.label)) },
                        )
                    }
                }
            }
        ) { padding ->
            Box(Modifier.padding(padding)) {
                ModeContent(mode, fullScreen = false, modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun ModeContent(mode: AppMode, fullScreen: Boolean, modifier: Modifier) {
    when (mode) {
        AppMode.Clock -> ClockScreen(fullScreen = fullScreen, modifier = modifier)
        AppMode.Pomodoro -> PomodoroScreen(modifier = modifier)
    }
}
