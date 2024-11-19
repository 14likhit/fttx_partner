package com.fttx.partner.domain.repository.ticket

import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.domain.model.TicketUpdate
import com.fttx.partner.domain.model.UserTicket

interface ITicketRepository {

    suspend fun getTicket(userId: Int): NetworkResultWrapper<UserTicket>
    suspend fun updateTicket(ticketId: Int, status: String,location: Pair<Double, Double>): NetworkResultWrapper<TicketUpdate>

}