package com.fttx.partner.domain.repository.login

import com.fttx.partner.data.network.util.NetworkResultWrapper

interface ILoginRepository {
    suspend fun login(userName: String, password: String): NetworkResultWrapper<Any>
}