package com.fttx.partner.data.source.remote.ticket

import retrofit2.Response
import retrofit2.http.GET

interface TicketApiServices {

    @GET("")
    suspend fun getTicket(): Response<Any>
}