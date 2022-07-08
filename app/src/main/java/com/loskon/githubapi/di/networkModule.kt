package com.loskon.githubapi.di

import android.content.Context
import com.loskon.githubapi.BuildConfig
import com.loskon.githubapi.app.base.moshi.LocalDateTimeMoshiAdapter
import com.loskon.githubapi.data.networkdatasource.NetworkDataSource
import com.loskon.githubapi.data.networkdatasource.api.GithubApi
import com.loskon.githubapi.data.networkdatasource.interceptor.CacheInterceptor
import com.squareup.moshi.Moshi
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single { provideLoggingInterceptor() }
    single { provideCache(androidApplication()) }
    single { provideOkHttp(androidApplication(), get(), get()) }
    single { provideMoshi() }
    single { provideRetrofit(get(), get()) }
    single { provideGithubApi(get()) }

    single { NetworkDataSource(get()) }
}

private const val CACHE_SIZE = 2 * 1024 * 1024L
private const val TIMEOUT = 30L

private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
}

private fun provideCache(context: Context): Cache {
    return Cache(context.cacheDir, CACHE_SIZE)
}

private fun provideOkHttp(context: Context, cache: Cache, logging: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder().apply {
        connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        readTimeout(TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        addInterceptor(CacheInterceptor(context))
        if (BuildConfig.DEBUG) addInterceptor(logging)
        cache(cache)
    }.build()
}

private fun provideMoshi(): Moshi {
    return Moshi.Builder().add(LocalDateTimeMoshiAdapter()).build()
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