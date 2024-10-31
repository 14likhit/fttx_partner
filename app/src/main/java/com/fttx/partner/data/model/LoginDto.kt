package com.fttx.partner.data.model

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("status") val status: String?,
    @SerializedName("message") val message: String?,
    @SerializedName("user_id") val userId: Int?,
)
