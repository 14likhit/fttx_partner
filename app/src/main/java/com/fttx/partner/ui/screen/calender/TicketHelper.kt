package com.fttx.partner.ui.screen.calender

import com.fttx.partner.ui.mvicore.IEffect
import com.fttx.partner.ui.mvicore.IIntent
import com.fttx.partner.ui.mvicore.IState

sealed class TicketIntent : IIntent {
    data object Init : TicketIntent()
}

sealed class TicketEffect: IEffect {
    data object NavigateToAddTicket : TicketEffect()
}

data class TicketState(
    val id: Int = -1
): IState