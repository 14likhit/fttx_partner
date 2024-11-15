package com.fttx.partner.data.model

import com.google.gson.annotations.SerializedName

data class TicketUpdateDto(
    @SerializedName("status") val status: String?,
    @SerializedName("message") val message: String?,
)
