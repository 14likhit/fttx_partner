package com.fttx.partner.data.network.base

import com.google.gson.annotations.SerializedName

data class BaseListResponse<T>(
    @SerializedName("items") val items: List<T>?,
    @SerializedName("offset") val offset: Int?,
    @SerializedName("limit") val limit: Int?,
    @SerializedName("count") val count: Int?,
)
