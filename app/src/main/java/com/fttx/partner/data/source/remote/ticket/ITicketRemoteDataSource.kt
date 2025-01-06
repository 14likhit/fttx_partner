package com.fttx.partner.data.source.remote.ticket

import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.domain.model.TicketUpdate
import com.fttx.partner.domain.model.TicketUpdateRequestBody
import com.fttx.partner.domain.model.UserTicket

interface ITicketRemoteDataSource {
    suspend fun getTicket(userId: Int): NetworkResultWrapper<UserTicket>
    suspend fun updateTicket(
        ticketUpdateRequestBody: TicketUpdateRequestBody
    ): NetworkResultWrapper<TicketUpdate>
}