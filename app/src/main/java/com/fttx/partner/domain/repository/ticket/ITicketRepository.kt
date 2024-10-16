package com.fttx.partner.domain.repository.ticket

import com.fttx.partner.data.network.util.NetworkResultWrapper

interface ITicketRepository {

    suspend fun getTicket(): NetworkResultWrapper<Any>

}