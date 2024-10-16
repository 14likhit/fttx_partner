package com.fttx.partner.data.source.remote.ticket

import com.fttx.partner.data.network.util.NetworkResultWrapper

interface ITicketRemoteDataSource {
    suspend fun getTicket(): NetworkResultWrapper<Any>
}