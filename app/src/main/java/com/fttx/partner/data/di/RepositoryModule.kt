package com.fttx.partner.data.di

import com.fttx.partner.data.repository.login.LoginRepositoryImpl
import com.fttx.partner.data.repository.ticket.TicketRepositoryImpl
import com.fttx.partner.data.source.remote.login.ILoginRemoteDataSource
import com.fttx.partner.data.source.remote.ticket.ITicketRemoteDataSource
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

}