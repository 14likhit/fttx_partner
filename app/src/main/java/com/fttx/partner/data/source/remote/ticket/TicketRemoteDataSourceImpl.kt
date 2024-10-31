package com.fttx.partner.data.source.remote.ticket

import android.util.Log
import com.fttx.partner.data.mapper.UserTicketMapper
import com.fttx.partner.data.network.service.ApiCallerService
import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.domain.model.UserTicket
import javax.inject.Inject

class TicketRemoteDataSourceImpl @Inject constructor(
    private val apiServices: TicketApiServices,
    private val apiCallerService: ApiCallerService,
) : ITicketRemoteDataSource {
    override suspend fun getTicket(userId: Int): NetworkResultWrapper<UserTicket> {
        apiCallerService.safeApiCall {
            apiServices.getTicket(userId)
        }.onSuccess {
            return NetworkResultWrapper.Success(UserTicketMapper.mapUserTicketDtoToUserTicket(it))
        }.onFailure {
            Log.e("Test","$it")
            return NetworkResultWrapper.Error()
        }
        return NetworkResultWrapper.Error()
    }
}