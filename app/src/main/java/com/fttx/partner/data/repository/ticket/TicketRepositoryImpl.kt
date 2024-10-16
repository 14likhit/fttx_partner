package com.fttx.partner.data.repository.ticket

import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.data.source.remote.ticket.ITicketRemoteDataSource
import com.fttx.partner.domain.repository.ticket.ITicketRepository
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
    private val ticketRemoteDataSource: ITicketRemoteDataSource
) : ITicketRepository {
    override suspend fun getTicket(): NetworkResultWrapper<Any> {
        return ticketRemoteDataSource.getTicket()
    }
}