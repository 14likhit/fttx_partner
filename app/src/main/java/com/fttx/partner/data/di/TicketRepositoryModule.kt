package com.fttx.partner.data.di

import com.fttx.partner.data.repository.TicketRepositoryImpl
import com.fttx.partner.data.source.remote.ITicketRemoteDataSource
import com.fttx.partner.domain.repository.ITicketRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TicketRepositoryModule {

    @Provides
    @Singleton
    fun provideTicketRepository(ticketRemoteDataSource: ITicketRemoteDataSource): ITicketRepository {
        return TicketRepositoryImpl(ticketRemoteDataSource)
    }

}