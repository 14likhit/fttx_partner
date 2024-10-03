package com.fttx.partner.data.repository

import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.data.source.remote.ITicketRemoteDataSource
import com.fttx.partner.domain.repository.ITicketRepository
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
    private val ticketRemoteDataSource: ITicketRemoteDataSource
) : ITicketRepository {
    override suspend fun getTicket(): NetworkResultWrapper<Any> {
        return ticketRemoteDataSource.getTicket()
    }
}