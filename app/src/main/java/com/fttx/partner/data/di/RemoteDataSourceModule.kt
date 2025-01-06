package com.fttx.partner.data.di

import com.fttx.partner.data.network.service.ApiCallerService
import com.fttx.partner.data.source.remote.agents.AgentRemoteDataSourceImpl
import com.fttx.partner.data.source.remote.agents.AgentsApiServices
import com.fttx.partner.data.source.remote.agents.IAgentRemoteDataSource
import com.fttx.partner.data.source.remote.location.ILocationRemoteDataSource
import com.fttx.partner.data.source.remote.location.LocationApiServices
import com.fttx.partner.data.source.remote.location.LocationRemoteDataSourceImpl
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

    @Provides
    @Singleton
    fun provideAgentsRemoteDataSource(
        api: AgentsApiServices,
        apiCallerService: ApiCallerService,
    ): IAgentRemoteDataSource {
        return AgentRemoteDataSourceImpl(api, apiCallerService)
    }

    @Provides
    @Singleton
    fun provideLocationRemoteDataSource(
        api: LocationApiServices,
        apiCallerService: ApiCallerService,
    ): ILocationRemoteDataSource {
        return LocationRemoteDataSourceImpl(api, apiCallerService)
    }
}