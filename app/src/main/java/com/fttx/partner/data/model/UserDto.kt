package com.fttx.partner.data.model

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id") val userId: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("mobile") val mobile: String?,
    @SerializedName("email") val email: String?,
)
