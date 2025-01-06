package com.fttx.partner.domain.repository.ticket

import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.domain.model.TicketUpdate
import com.fttx.partner.domain.model.TicketUpdateRequestBody
import com.fttx.partner.domain.model.UserTicket

interface ITicketRepository {

    suspend fun getTicket(userId: Int): NetworkResultWrapper<UserTicket>
    suspend fun updateTicket(
        ticketUpdateRequestBody: TicketUpdateRequestBody
    ): NetworkResultWrapper<TicketUpdate>

}