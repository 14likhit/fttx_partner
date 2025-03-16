package com.fttx.partner.ui.screen.home

import com.fttx.partner.ui.mvicore.IEffect
import com.fttx.partner.ui.mvicore.IIntent
import com.fttx.partner.ui.mvicore.IState

sealed class MainIntent : IIntent {
    data object CheckIn: MainIntent()
    data object CheckOut: MainIntent()
}

sealed class MainEffect : IEffect {}

data class MainState(
    val isCheckedIn: Boolean = false,
    val isCheckedOut: Boolean = false,
    val hideProgress: Boolean = false,
) : IState