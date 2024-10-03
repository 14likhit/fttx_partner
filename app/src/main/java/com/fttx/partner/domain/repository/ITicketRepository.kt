package com.fttx.partner.domain.repository

import com.fttx.partner.data.network.util.NetworkResultWrapper

interface ITicketRepository {

    suspend fun getTicket(): NetworkResultWrapper<Any>

}