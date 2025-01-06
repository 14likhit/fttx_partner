package com.fttx.partner.data.source.remote.location

import com.fttx.partner.data.network.service.ApiCallerService
import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.domain.model.LocationUpdateRequestBody
import javax.inject.Inject

class LocationRemoteDataSourceImpl @Inject constructor(
    private val apiServices: LocationApiServices,
    private val apiCallerService: ApiCallerService,
) : ILocationRemoteDataSource {

    override suspend fun updateLocation(locationUpdateRequestBody: LocationUpdateRequestBody): NetworkResultWrapper<Unit> {
        apiCallerService.safeApiCall {
            apiServices.updateLocationLog(locationUpdateRequestBody)
        }.onSuccess {
            return NetworkResultWrapper.Success(Unit)
        }.onFailure {
            return NetworkResultWrapper.Error()
        }
        return NetworkResultWrapper.Error()
    }
}