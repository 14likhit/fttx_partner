package com.fttx.partner.data.source.remote.login

import com.fttx.partner.data.mapper.LoginMapper
import com.fttx.partner.data.network.service.ApiCallerService
import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.domain.model.Login
import com.fttx.partner.domain.model.LoginRequestBody
import javax.inject.Inject

class LoginRemoteDataSourceImpl @Inject constructor(
    private val apiServices: LoginApiServices,
    private val apiCallerService: ApiCallerService,
) : ILoginRemoteDataSource {
    override suspend fun login(
        userName: String,
        password: String,
        deviceId: String
    ): NetworkResultWrapper<Login> {
        apiCallerService.safeApiCall {
            apiServices.login(LoginRequestBody(userName, password, deviceId))
        }.onSuccess {
            return NetworkResultWrapper.Success(LoginMapper.mapLoginDtoToLogin(it))
        }.onFailure {
            return NetworkResultWrapper.Error()
        }
        return NetworkResultWrapper.Error()
    }
}