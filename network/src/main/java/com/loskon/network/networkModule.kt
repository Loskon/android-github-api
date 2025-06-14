package com.loskon.network

import com.loskon.network.api.GithubApi
import com.loskon.network.moshiadapter.LocalDateTimeMoshiAdapter
import com.loskon.network.source.NetworkDataSource
import com.loskon.network.source.NetworkPagingDataSource
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideLoggingInterceptor() }
    single { provideOkHttp(get()) }
    single { provideMoshi() }
    single { provideRetrofit(get(), get()) }
    single { provideGithubApi(get()) }

    single { NetworkDataSource(get()) }
    single { NetworkPagingDataSource(get()) }
}

private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
}

@Suppress("MagicNumber")
private fun provideOkHttp(logging: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder().apply {
        connectTimeout(30L, TimeUnit.SECONDS)
        readTimeout(30L, TimeUnit.SECONDS)
        writeTimeout(60L, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) addInterceptor(logging)
    }.build()
}

private fun provideMoshi(): Moshi {
    return Moshi.Builder().addLast(KotlinJsonAdapterFactory()).add(LocalDateTimeMoshiAdapter()).build()
}

private fun provideRetrofit(okHttp: OkHttpClient, moshi: Moshi): Retrofit {
    return Retrofit.Builder()
        .client(okHttp)
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}

private fun provideGithubApi(retrofit: Retrofit): GithubApi {
    return retrofit.create(GithubApi::class.java)
}