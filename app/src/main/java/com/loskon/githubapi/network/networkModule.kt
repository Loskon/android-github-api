package com.loskon.githubapi.network

import android.content.Context
import com.loskon.githubapi.BuildConfig
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

    single { provideCache(androidContext()) }
    single { provideOkHttp(get(), androidContext()) }
    single { provideRetrofit(get()) }
    single { provideGithubApi(get()) }

    single { NetworkDataSource(get()) }
}

const val CACHE_DIR_NAME = "users"
private const val CACHE_SIZE = 2 * 1024 * 1024L
private const val TIMEOUT = 30L

private val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private fun provideCache(context: Context): Cache {
    val cacheDir = File(context.cacheDir, CACHE_DIR_NAME)
    return Cache(cacheDir, CACHE_SIZE)
}

private fun provideOkHttp(cache: Cache, context: Context): OkHttpClient {
    return OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) addInterceptor(loggingInterceptor)
        connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        readTimeout(TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        addInterceptor(CacheInterceptor(context))
        cache(cache)
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