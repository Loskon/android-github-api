package com.loskon.githubapi.network.retrofit

import android.content.Context
import com.loskon.githubapi.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

val networkModule = module {

    single { provideCache(androidContext()) }
    single { provideOkHttp(get(), androidContext()) }
    single { provideRetrofit(get()) }
    single { provideGithubApi(get()) }

    single { NetworkDataSource(get()) }
}

const val CACHE_DIR_NAME = "users"

private fun provideCache(context: Context): Cache {
    val cacheDir = File(context.cacheDir, CACHE_DIR_NAME)
    val cacheSize = 5 * 1024 * 1024L // 5 MB
    return Cache(cacheDir, cacheSize)
}

private fun provideOkHttp(cache: Cache, context: Context): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(CacheInterceptor(context))
        .cache(cache)
        .build()
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