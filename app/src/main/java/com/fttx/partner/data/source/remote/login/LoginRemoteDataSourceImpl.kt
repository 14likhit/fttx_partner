package com.fttx.partner.data.source.remote.login

import com.fttx.partner.data.network.service.ApiCallerService
import com.fttx.partner.data.network.util.NetworkResultWrapper
import javax.inject.Inject

class LoginRemoteDataSourceImpl @Inject constructor(
    private val apiServices: LoginApiServices,
    private val apiCallerService: ApiCallerService,
) : ILoginRemoteDataSource {
    override suspend fun login(userName: String, password: String): NetworkResultWrapper<Any> {
        apiCallerService.safeApiCall {
            apiServices.login(userName, password)
        }.onSuccess {
            return NetworkResultWrapper.Success(Unit)
        }.onFailure {
            return NetworkResultWrapper.Error()
        }
        return NetworkResultWrapper.Error()
    }
}