package com.fttx.partner.ui.screen.form

import com.fttx.partner.domain.model.Ticket
import com.fttx.partner.ui.compose.model.UserUiModel
import com.fttx.partner.ui.mvicore.IEffect
import com.fttx.partner.ui.mvicore.IIntent
import com.fttx.partner.ui.mvicore.IState

sealed class TicketFormIntent : IIntent {
    data object Init : TicketFormIntent()
    data object BackCta : TicketFormIntent()
    data class TicketCardCta(val ticket: Ticket) : TicketFormIntent()
    data object AddCta : TicketFormIntent()
    data class UpdateTicketCta(val ticketId: Int, val ticketStatus: String) : TicketFormIntent()
    data class UpdateTicket(
        val ticketId: Int,
        val ticketStatus: String,
        val location: Pair<Double, Double>
    ) : TicketFormIntent()
    data object NavigateToAgentBottomSheet : TicketFormIntent()
    data object DismissAgentBottomSheet : TicketFormIntent()
    data class SaveAssociateCta(val selectedAgents: List<UserUiModel>): TicketFormIntent()
    data object EmptyError : TicketFormIntent()
}

sealed class TicketFormEffect : IEffect {
    data object NavigateBack : TicketFormEffect()
    data class NavigateToTicketDetails(val ticket: Ticket) : TicketFormEffect()
    data object NavigateToAddTicket : TicketFormEffect()
    data object NavigateToTicketList : TicketFormEffect()
    data class FetchLocation(val ticketId: Int, val ticketStatus: String) : TicketFormEffect()
}

data class TicketFormState(
    val id: Int = -1,
    val isLoading: Boolean = false,
    val error: String = "",
    val showAgentBottomSheet: Boolean = false,
    val allAgents: List<UserUiModel> = emptyList(),
    val selectedAgents: List<UserUiModel> = emptyList(),
) : IState

enum class TicketFormScreenType(val type: String) {
    AddTicket("add_ticket"),
    EditTicket("edit_ticket")
}