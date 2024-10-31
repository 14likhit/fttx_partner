package com.fttx.partner.data.source.remote.ticket

import com.fttx.partner.data.model.UserTicketDto
import com.fttx.partner.data.network.util.EndPoints.Ticket.QUERY_USER_ID
import com.fttx.partner.data.network.util.EndPoints.Ticket.USER_TICKET
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface TicketApiServices {

    @POST(USER_TICKET)
    suspend fun getTicket(
        @Query(QUERY_USER_ID) userId: Int
    ): Response<UserTicketDto>
}