package com.fttx.partner.data.source.remote.ticket

import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.domain.model.UserTicket

interface ITicketRemoteDataSource {
    suspend fun getTicket(userId: Int): NetworkResultWrapper<UserTicket>
}