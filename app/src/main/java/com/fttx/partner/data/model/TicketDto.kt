package com.fttx.partner.data.model

import com.google.gson.annotations.SerializedName

data class TicketDto(
    @SerializedName("ticket_id") val id: String?,
    @SerializedName("subject") val title: String?,
    val time: String?,
    val category: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("priority") val priority: String?,
    @SerializedName("customer_name") val customerName: String?,
    val customer: CustomerDto?,
)
