package com.fttx.partner.ui.screen.home

import com.fttx.partner.domain.model.Ticket
import com.fttx.partner.ui.mvicore.IEffect
import com.fttx.partner.ui.mvicore.IIntent
import com.fttx.partner.ui.mvicore.IState

sealed class HomeIntent : IIntent {
    data object Init : HomeIntent()
    data object BackCta : HomeIntent()
    data class TicketCardCta(val ticket: Ticket) : HomeIntent()
    data object AddCta : HomeIntent()
}

sealed class HomeEffect : IEffect {
    data object NavigateBack : HomeEffect()
    data class NavigateToTicketDetails(val ticket: Ticket) : HomeEffect()
    data object NavigateToAddTicket : HomeEffect()
}

data class HomeState(
    val id: Int = -1
) : IState