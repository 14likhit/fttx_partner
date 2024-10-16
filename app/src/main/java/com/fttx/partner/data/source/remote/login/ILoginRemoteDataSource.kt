package com.fttx.partner.data.source.remote.login

import com.fttx.partner.data.network.util.NetworkResultWrapper

interface ILoginRemoteDataSource {
    suspend fun login(userName: String, password: String): NetworkResultWrapper<Any>
}