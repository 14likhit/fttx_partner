package com.fttx.partner.data.source.remote.ticket

import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.domain.model.TicketUpdate
import com.fttx.partner.domain.model.UserTicket

interface ITicketRemoteDataSource {
    suspend fun getTicket(userId: Int): NetworkResultWrapper<UserTicket>
    suspend fun updateTicket(
        ticketId: Int,
        status: String,
        location: Pair<Double, Double>
    ): NetworkResultWrapper<TicketUpdate>
}