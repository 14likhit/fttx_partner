package com.fttx.partner.data.di

import com.fttx.partner.data.network.service.ApiCallerService
import com.fttx.partner.data.source.remote.login.ILoginRemoteDataSource
import com.fttx.partner.data.source.remote.login.LoginApiServices
import com.fttx.partner.data.source.remote.login.LoginRemoteDataSourceImpl
import com.fttx.partner.data.source.remote.ticket.ITicketRemoteDataSource
import com.fttx.partner.data.source.remote.ticket.TicketApiServices
import com.fttx.partner.data.source.remote.ticket.TicketRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {

    @Provides
    @Singleton
    fun provideLoginRemoteDataSource(
        api: LoginApiServices,
        apiCallerService: ApiCallerService,
    ): ILoginRemoteDataSource {
        return LoginRemoteDataSourceImpl(api, apiCallerService)
    }

    @Provides
    @Singleton
    fun provideTicketRemoteDataSource(
        api: TicketApiServices,
        apiCallerService: ApiCallerService,
    ): ITicketRemoteDataSource {
        return TicketRemoteDataSourceImpl(api, apiCallerService)
    }
}