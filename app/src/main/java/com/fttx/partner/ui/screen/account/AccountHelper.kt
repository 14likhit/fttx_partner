package com.fttx.partner.ui.screen.account

import com.fttx.partner.domain.model.Ticket
import com.fttx.partner.ui.mvicore.IEffect
import com.fttx.partner.ui.mvicore.IIntent
import com.fttx.partner.ui.mvicore.IState
import com.fttx.partner.ui.screen.home.HomeEffect
import com.fttx.partner.ui.screen.home.HomeIntent

sealed class AccountIntent : IIntent {

}

sealed class AccountEffect : IEffect {

}

data class AccountState(
    val id: Int = -1
) : IState