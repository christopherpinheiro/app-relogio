package com.apprelogio.ui.clock

import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import com.apprelogio.R
import java.time.format.DateTimeFormatter
import java.util.Locale

/** Fonte de display de 7 segmentos (DSEG7, licença OFL), como relógio de mesa. */
private val SevenSegment = FontFamily(Font(R.font.dseg7_classic_regular))

/**
 * Tela do relógio. Com [fullScreen] (paisagem) usa fundo preto e
 * dimensiona os dígitos para ocupar a largura da tela. Em paisagem também
 * mostra um ícone no canto enquanto o aparelho está carregando.
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
        // "88:88" na fonte de 7 segmentos ocupa cerca de 3,5x o tamanho da fonte.
        val timeSize: Dp = if (fullScreen) min(maxWidth * 0.26f, maxHeight * 0.5f) else maxWidth * 0.22f
        val timeSp = with(LocalDensity.current) { timeSize.toSp() }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            SegmentText(text = time, color = foreground, fontSize = timeSp)
            Spacer(Modifier.size(timeSize * 0.12f))
            SegmentText(text = seconds, color = foreground.copy(alpha = 0.6f), fontSize = timeSp * 0.25f)
            Spacer(Modifier.size(timeSize * 0.08f))
            Text(
                text = date.replaceFirstChar { it.titlecase(locale) },
                color = foreground.copy(alpha = 0.6f),
                fontSize = if (fullScreen) 20.sp else 18.sp,
            )
        }

        if (fullScreen) {
            ChargingIndicator(
                color = foreground.copy(alpha = 0.6f),
                modifier = Modifier.align(Alignment.TopEnd).padding(24.dp),
            )
        }
    }
}

/**
 * Texto na fonte de 7 segmentos, com os segmentos apagados ("88:88") bem
 * fracos por trás, como num visor de relógio de mesa.
 */
@Composable
private fun SegmentText(text: String, color: Color, fontSize: TextUnit) {
    val style = TextStyle(fontFamily = SevenSegment, fontSize = fontSize)
    Box {
        Text(
            text = text.map { if (it.isDigit()) '8' else it }.joinToString(""),
            color = color.copy(alpha = color.alpha * 0.08f),
            style = style,
            maxLines = 1,
        )
        Text(text = text, color = color, style = style, maxLines = 1)
    }
}

/** Raio e porcentagem da bateria; aparece enquanto o carregador estiver conectado. */
@Composable
private fun ChargingIndicator(color: Color, modifier: Modifier = Modifier) {
    val battery = rememberBatteryStatus()
    if (!battery.charging) return

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(R.drawable.ic_battery_charging),
            contentDescription = stringResource(R.string.charging),
            tint = color,
            modifier = Modifier.size(24.dp),
        )
        Spacer(Modifier.width(4.dp))
        Text(text = "${battery.percent}%", color = color, fontSize = 18.sp)
    }
}
