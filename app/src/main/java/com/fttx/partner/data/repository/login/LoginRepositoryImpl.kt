package com.fttx.partner.data.repository.login

import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.data.source.remote.login.ILoginRemoteDataSource
import com.fttx.partner.domain.repository.login.ILoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: ILoginRemoteDataSource,
) : ILoginRepository {

    override suspend fun login(userName: String, password: String): NetworkResultWrapper<Any> {
        return loginRemoteDataSource.login(userName, password)
    }
}