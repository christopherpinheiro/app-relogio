package com.apprelogio.ui.clock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

/** Estado da bateria: se o carregador está conectado e a carga em porcentagem. */
data class BatteryStatus(val charging: Boolean, val percent: Int)

/** Estado da bateria, atualizado sempre que o sistema avisa uma mudança. */
@Composable
fun rememberBatteryStatus(): BatteryStatus {
    val context = LocalContext.current
    var status by remember { mutableStateOf(BatteryStatus(charging = false, percent = 0)) }
    DisposableEffect(context) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                status = intent.toBatteryStatus()
            }
        }
        // O aviso de bateria é "sticky": o registro já devolve o estado atual.
        ContextCompat.registerReceiver(
            context,
            receiver,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED),
            ContextCompat.RECEIVER_NOT_EXPORTED,
        )?.let { status = it.toBatteryStatus() }
        onDispose { context.unregisterReceiver(receiver) }
    }
    return status
}

private fun Intent.toBatteryStatus(): BatteryStatus {
    // Usa "cabo conectado" em vez do status de carga: muitos aparelhos pausam
    // a carga (proteção da bateria, carga adaptativa) mesmo com o cabo ligado.
    val plugged = getIntExtra(BatteryManager.EXTRA_PLUGGED, 0)
    val level = getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
    val scale = getIntExtra(BatteryManager.EXTRA_SCALE, 100)
    return BatteryStatus(
        charging = plugged != 0,
        percent = if (level >= 0 && scale > 0) level * 100 / scale else 0,
    )
}
