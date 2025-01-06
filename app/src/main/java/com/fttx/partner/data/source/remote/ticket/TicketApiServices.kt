package com.fttx.partner.data.source.remote.ticket

import com.fttx.partner.data.model.TicketUpdateDto
import com.fttx.partner.data.model.UserTicketDto
import com.fttx.partner.data.network.util.EndPoints.Ticket.QUERY_AGENT_IDS
import com.fttx.partner.data.network.util.EndPoints.Ticket.QUERY_LATITUDE
import com.fttx.partner.data.network.util.EndPoints.Ticket.QUERY_LONGITUDE
import com.fttx.partner.data.network.util.EndPoints.Ticket.QUERY_STATUS
import com.fttx.partner.data.network.util.EndPoints.Ticket.QUERY_TICKET_ID
import com.fttx.partner.data.network.util.EndPoints.Ticket.QUERY_USER_ID
import com.fttx.partner.data.network.util.EndPoints.Ticket.UPDATE_TICKET
import com.fttx.partner.data.network.util.EndPoints.Ticket.USER_TICKET
import com.fttx.partner.domain.model.TicketUpdateRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface TicketApiServices {

    @POST(USER_TICKET)
    suspend fun getTicket(
        @Query(QUERY_USER_ID) userId: Int
    ): Response<UserTicketDto>

    @POST(UPDATE_TICKET)
    suspend fun updateTicket(
        @Body ticketUpdateRequestBody: TicketUpdateRequestBody,
    ): Response<TicketUpdateDto>
}