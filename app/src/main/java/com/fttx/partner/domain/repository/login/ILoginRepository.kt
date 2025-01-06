package com.fttx.partner.domain.repository.login

import com.fttx.partner.data.network.util.NetworkResultWrapper
import com.fttx.partner.domain.model.Login

interface ILoginRepository {
    suspend fun login(
        userName: String,
        password: String,
        deviceId: String
    ): NetworkResultWrapper<Login>
}