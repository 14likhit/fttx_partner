package com.fttx.partner.data.model

import com.google.gson.annotations.SerializedName

data class AgentsDto(
    @SerializedName("status") val status: String?,
    @SerializedName("agents") val agents: List<UserDto>?,
)