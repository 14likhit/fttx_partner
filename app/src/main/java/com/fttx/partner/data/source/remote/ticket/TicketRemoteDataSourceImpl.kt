package com.fttx.partner.data.source.remote.ticket

import android.util.Log
import com.fttx.partner.data.mapper.TicketUpdateMapper
import com.fttx.partner.data.mapper.UserTicketMapper
import com.fttx.partner.data.network.service.ApiCallerService
import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.domain.model.TicketUpdate
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
            return NetworkResultWrapper.Error()
        }
        return NetworkResultWrapper.Error()
    }

    override suspend fun updateTicket(
        ticketId: Int,
        status: String
    ): NetworkResultWrapper<TicketUpdate> {
        apiCallerService.safeApiCall {
            apiServices.updateTicket(ticketId, status)
        }.onSuccess {
            return NetworkResultWrapper.Success(
                TicketUpdateMapper.mapTicketUpdateDtoToTicketUpdate(
                    it
                )
            )
        }.onFailure {
            return NetworkResultWrapper.Error()
        }
        return NetworkResultWrapper.Error()
    }
}