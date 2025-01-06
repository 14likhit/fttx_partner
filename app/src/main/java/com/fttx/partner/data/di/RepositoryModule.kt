package com.fttx.partner.data.di

import com.fttx.partner.data.repository.agents.AgentRepositoryImpl
import com.fttx.partner.data.repository.location.LocationRepositoryImpl
import com.fttx.partner.data.repository.login.LoginRepositoryImpl
import com.fttx.partner.data.repository.ticket.TicketRepositoryImpl
import com.fttx.partner.data.source.remote.agents.IAgentRemoteDataSource
import com.fttx.partner.data.source.remote.location.ILocationRemoteDataSource
import com.fttx.partner.data.source.remote.login.ILoginRemoteDataSource
import com.fttx.partner.data.source.remote.ticket.ITicketRemoteDataSource
import com.fttx.partner.domain.repository.agents.IAgentRepository
import com.fttx.partner.domain.repository.location.ILocationRepository
import com.fttx.partner.domain.repository.login.ILoginRepository
import com.fttx.partner.domain.repository.ticket.ITicketRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLoginRepository(loginRemoteDataSource: ILoginRemoteDataSource): ILoginRepository {
        return LoginRepositoryImpl(loginRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideTicketRepository(ticketRemoteDataSource: ITicketRemoteDataSource): ITicketRepository {
        return TicketRepositoryImpl(ticketRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideAgentRepository(agentRemoteDataSource: IAgentRemoteDataSource): IAgentRepository {
        return AgentRepositoryImpl(agentRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(locationRemoteDataSource: ILocationRemoteDataSource): ILocationRepository {
        return LocationRepositoryImpl(locationRemoteDataSource)
    }

}