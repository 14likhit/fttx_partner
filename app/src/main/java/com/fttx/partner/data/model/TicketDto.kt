package com.fttx.partner.data.model

import com.google.gson.annotations.SerializedName

data class TicketDto(
    @SerializedName("ticket_id") val id: String?,
    @SerializedName("subject") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("ttype") val ticketType: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("priority") val priority: String?,
    @SerializedName("customer_name") val customerName: String?,
    @SerializedName("customer_mobile") val customerMobile: String?,
    @SerializedName("customer_address") val customerAddress: String?,
)
