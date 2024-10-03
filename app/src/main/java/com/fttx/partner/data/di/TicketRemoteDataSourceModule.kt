package com.fttx.partner.data.di

import com.fttx.partner.data.network.service.ApiCallerService
import com.fttx.partner.data.source.remote.ITicketRemoteDataSource
import com.fttx.partner.data.source.remote.TicketApiServices
import com.fttx.partner.data.source.remote.TicketRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TicketRemoteDataSourceModule {

    @Provides
    @Singleton
    fun provideTicketRemoteDataSource(
        api: TicketApiServices,
        apiCallerService: ApiCallerService,
    ): ITicketRemoteDataSource {
        return TicketRemoteDataSourceImpl(api,apiCallerService)
    }
}