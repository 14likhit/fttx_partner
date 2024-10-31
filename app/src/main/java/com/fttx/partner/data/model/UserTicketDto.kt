package com.fttx.partner.data.model

import com.google.gson.annotations.SerializedName

data class UserTicketDto(
    @SerializedName("status") val status: String?,
    @SerializedName("agent") val user: UserDto?,
    @SerializedName("tickets") val tickets: List<TicketDto>?,
)