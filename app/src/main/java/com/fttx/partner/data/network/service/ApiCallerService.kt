package com.fttx.partner.data.network.service

import retrofit2.Response

interface ApiCallerService {
    suspend fun <T> safeApiCall(
        apiCallBlock: suspend () -> Response<T>,
    ): Result<T>
}
