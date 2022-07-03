package com.loskon.githubapi.di

import android.content.Context
import com.loskon.githubapi.BuildConfig
import com.loskon.githubapi.data.networkdatasource.NetworkDataSource
import com.loskon.githubapi.data.networkdatasource.api.GithubApi
import com.loskon.githubapi.data.networkdatasource.interceptor.CacheInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

val networkModule = module {

    single { provideOkHttp(androidContext()) }
    single { provideRetrofit(get()) }
    single { provideGithubApi(get()) }

    single { NetworkDataSource(get()) }
}

const val CACHE_DIR_NAME = "users"
private const val CACHE_SIZE = 2 * 1024 * 1024L
private const val CONNECT_TIMEOUT = 30L

private fun getLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
}

private fun getCache(context: Context): Cache {
    val cacheDir = File(context.cacheDir, CACHE_DIR_NAME)
    return Cache(cacheDir, CACHE_SIZE)
}

private fun provideOkHttp(context: Context): OkHttpClient {
    return OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) addInterceptor(getLoggingInterceptor())
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        addInterceptor(CacheInterceptor(context))
        cache(getCache(context))
    }.build()
}

private fun provideRetrofit(okHttp: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(okHttp)
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideGithubApi(retrofit: Retrofit): GithubApi {
    return retrofit.create(GithubApi::class.java)
}