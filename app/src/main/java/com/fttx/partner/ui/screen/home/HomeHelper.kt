package com.fttx.partner.ui.screen.home

import com.fttx.partner.ui.mvicore.IEffect
import com.fttx.partner.ui.mvicore.IIntent
import com.fttx.partner.ui.mvicore.IState

sealed class HomeIntent : IIntent {
    data object Init : HomeIntent()
}

sealed class HomeEffect: IEffect {
    data object NavigateToAddHome : HomeEffect()
}

data class HomeState(
    val id: Int = -1
): IState