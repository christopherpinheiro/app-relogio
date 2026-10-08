package com.apprelogio.ui.clock

import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Tela do relógio. Com [fullScreen] (paisagem) usa fundo preto e
 * dimensiona os dígitos para ocupar a largura da tela.
 */
@Composable
fun ClockScreen(fullScreen: Boolean, modifier: Modifier = Modifier) {
    val now = rememberCurrentTime()
    val is24h = DateFormat.is24HourFormat(LocalContext.current)
    val locale = Locale.getDefault()
    val time = now.format(DateTimeFormatter.ofPattern(if (is24h) "HH:mm" else "h:mm", locale))
    val seconds = now.format(DateTimeFormatter.ofPattern("ss", locale))
    val date = now.format(DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM", locale))

    val background = if (fullScreen) Color.Black else MaterialTheme.colorScheme.background
    val foreground = if (fullScreen) Color.White else MaterialTheme.colorScheme.onBackground

    BoxWithConstraints(
        modifier = modifier.background(background),
        contentAlignment = Alignment.Center,
    ) {
        // Dígitos proporcionais à tela, limitados pela altura para não cortar.
        val timeSize: Dp = if (fullScreen) min(maxWidth * 0.30f, maxHeight * 0.55f) else maxWidth * 0.22f
        val timeSp = with(LocalDensity.current) { timeSize.toSp() }
        val digits = TextStyle(fontFeatureSettings = "tnum", fontWeight = FontWeight.Light)

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = time, color = foreground, fontSize = timeSp, style = digits, maxLines = 1)
            Text(
                text = seconds,
                color = foreground.copy(alpha = 0.6f),
                fontSize = timeSp * 0.25f,
                style = digits,
            )
            Text(
                text = date.replaceFirstChar { it.titlecase(locale) },
                color = foreground.copy(alpha = 0.6f),
                fontSize = if (fullScreen) 20.sp else 18.sp,
            )
        }
    }
}
