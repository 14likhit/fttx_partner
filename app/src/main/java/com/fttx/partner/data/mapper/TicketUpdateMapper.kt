package com.fttx.partner.data.mapper

import com.fttx.partner.data.model.TicketUpdateDto
import com.fttx.partner.domain.model.TicketUpdate

object TicketUpdateMapper {

    fun mapTicketUpdateDtoToTicketUpdate(ticketUpdateDto: TicketUpdateDto): TicketUpdate {
        return TicketUpdate(
            status = ticketUpdateDto.status ?: "",
            message = ticketUpdateDto.message ?: "",
        )

    }
}