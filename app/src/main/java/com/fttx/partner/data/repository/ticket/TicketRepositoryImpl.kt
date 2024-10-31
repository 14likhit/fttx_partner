package com.fttx.partner.data.repository.ticket

import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.data.source.remote.ticket.ITicketRemoteDataSource
import com.fttx.partner.domain.model.UserTicket
import com.fttx.partner.domain.repository.ticket.ITicketRepository
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
    private val ticketRemoteDataSource: ITicketRemoteDataSource
) : ITicketRepository {
    override suspend fun getTicket(userId: Int): NetworkResultWrapper<UserTicket> {
        return ticketRemoteDataSource.getTicket(userId)
    }
}