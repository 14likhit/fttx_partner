package com.fttx.partner.domain.repository.ticket

import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.domain.model.UserTicket

interface ITicketRepository {

    suspend fun getTicket(userId: Int): NetworkResultWrapper<UserTicket>

}