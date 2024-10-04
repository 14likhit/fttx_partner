package com.fttx.partner.data.di

import com.fttx.partner.data.network.util.OkHttpConstants.DEFAULT_CONNECT_TIMEOUT_IN_SEC
import com.fttx.partner.data.network.util.OkHttpConstants.DEFAULT_READ_TIMEOUT_IN_SEC
import com.fttx.partner.data.network.util.OkHttpConstants.DEFAULT_WRITE_TIMEOUT_IN_SEC
import com.fttx.partner.data.network.util.RequestHeaderConstants.REQUEST_ID
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideInterceptor() =
        Interceptor { chain ->
            val builder = chain.request().newBuilder()
            val requestId = UUID.randomUUID().toString()
            builder.addHeader(REQUEST_ID, requestId)
            val response = chain.proceed(builder.build())
            response
        }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        headersInterceptor: Interceptor,
        logging: HttpLoggingInterceptor,
    ): OkHttpClient {
        val okHttpBuilder =
            OkHttpClient.Builder()
                .readTimeout(DEFAULT_READ_TIMEOUT_IN_SEC, TimeUnit.SECONDS)
                .connectTimeout(
                    DEFAULT_CONNECT_TIMEOUT_IN_SEC,
                    TimeUnit.SECONDS,
                )
                .writeTimeout(DEFAULT_WRITE_TIMEOUT_IN_SEC, TimeUnit.SECONDS)
                .addInterceptor(headersInterceptor)
                .addNetworkInterceptor(logging)
        return okHttpBuilder.build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .serializeNulls() // To allow sending null values
            .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://rpc.semaai.com")
            .build()
    }
}
