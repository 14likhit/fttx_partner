package com.fttx.partner.data.di

import com.fttx.partner.data.source.remote.TicketApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TicketApiServiceModule {
    @Provides
    @Singleton
    fun provideLoyaltyApiServices(retrofit: Retrofit): TicketApiServices {
        return retrofit.create(TicketApiServices::class.java)
    }
}
