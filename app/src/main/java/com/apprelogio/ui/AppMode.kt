package com.apprelogio.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.apprelogio.R

/** Modos do app. Para adicionar um modo novo, acrescente aqui e trate em [AppRelogio]. */
enum class AppMode(@StringRes val label: Int, @DrawableRes val icon: Int) {
    Clock(R.string.mode_clock, R.drawable.ic_clock),
    Pomodoro(R.string.mode_pomodoro, R.drawable.ic_timer),
}
