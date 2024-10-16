package com.fttx.partner.data.source.remote.ticket

import com.fttx.partner.data.network.service.ApiCallerService
import com.fttx.partner.data.network.util.NetworkResultWrapper
import javax.inject.Inject

class TicketRemoteDataSourceImpl @Inject constructor(
    private val apiServices: TicketApiServices,
    private val apiCallerService: ApiCallerService,
) : ITicketRemoteDataSource {
    override suspend fun getTicket(): NetworkResultWrapper<Any> {
        apiCallerService.safeApiCall {
            apiServices.getTicket()
        }.onSuccess {
            return NetworkResultWrapper.Success(Unit)
        }.onFailure {
            return NetworkResultWrapper.Error()
        }
        return NetworkResultWrapper.Error()
    }
}