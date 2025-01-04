package com.fttx.partner.data.mapper

import com.fttx.partner.data.model.TicketDto
import com.fttx.partner.data.model.UserDto
import com.fttx.partner.data.model.UserTicketDto
import com.fttx.partner.domain.model.Customer
import com.fttx.partner.domain.model.Ticket
import com.fttx.partner.domain.model.User
import com.fttx.partner.domain.model.UserTicket

object UserTicketMapper {
    fun mapUserTicketDtoToUserTicket(userTicketDto: UserTicketDto): UserTicket {
        return UserTicket(
            status = userTicketDto.status ?: "",
            user = mapUserDtoToUser(userTicketDto.user),
            tickets = userTicketDto.tickets?.map { mapTicketDtoToTicket(it) }.orEmpty()
        )
    }


    fun mapUserDtoToUser(userDto: UserDto?): User {
        return User(
            userId = userDto?.userId ?: 0,
            name = userDto?.name ?: "",
            mobile = userDto?.mobile ?: "",
            email = userDto?.email ?: "",
            isAdmin = userDto?.isAdmin ?: false
        )
    }

    private fun mapTicketDtoToTicket(ticketDto: TicketDto?): Ticket {
        return Ticket(
            id = ticketDto?.id ?: "",
            title = ticketDto?.title ?: "",
            description = ticketDto?.description ?: "",
            ticketType = ticketDto?.ticketType ?: "",
            status = ticketDto?.status ?: "",
            priority = ticketDto?.priority ?: "",
            customerName = ticketDto?.customerName ?: "",
            customerAddress = ticketDto?.customerAddress ?: "",
            customerMobile = ticketDto?.customerMobile ?: "",
        )
    }
}