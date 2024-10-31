package com.fttx.partner.data.source.remote.login

import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.domain.model.Login

interface ILoginRemoteDataSource {
    suspend fun login(userName: String, password: String): NetworkResultWrapper<Login>
}