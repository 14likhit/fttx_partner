package com.fttx.partner.data.di

import com.fttx.partner.data.source.remote.login.LoginApiServices
import com.fttx.partner.data.source.remote.ticket.TicketApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun provideLoginApiServices(retrofit: Retrofit): LoginApiServices {
        return retrofit.create(LoginApiServices::class.java)
    }

    @Provides
    @Singleton
    fun provideTicketApiServices(retrofit: Retrofit): TicketApiServices {
        return retrofit.create(TicketApiServices::class.java)
    }
}
