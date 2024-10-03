package com.fttx.partner.data.di

import com.fttx.partner.data.network.service.ApiCallerService
import com.fttx.partner.data.network.service.ApiCallerServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkServiceModule {
    @Binds
    abstract fun bindApiCallerService(apiCallerService: ApiCallerServiceImpl): ApiCallerService
}