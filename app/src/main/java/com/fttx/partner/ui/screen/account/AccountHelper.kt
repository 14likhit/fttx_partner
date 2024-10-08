package com.fttx.partner.ui.screen.account

import com.fttx.partner.ui.mvicore.IEffect
import com.fttx.partner.ui.mvicore.IIntent
import com.fttx.partner.ui.mvicore.IState

sealed class AccountIntent : IIntent {
    data object LogoutCta : AccountIntent()
}

sealed class AccountEffect : IEffect {
    data object NavigateToLoginScreen : AccountEffect()
}

data class AccountState(
    val id: Int = -1
) : IState