package com.loskon.network

import com.loskon.network.api.GithubApi
import com.loskon.network.moshiadapter.DateTimeMoshiAdapter
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
    single { provideOkHttp() }
    single { provideRetrofit(get()) }
    single { provideGithubApi(get()) }
    single { NetworkDataSource(get()) }
    single { NetworkPagingDataSource(get()) }
}

@Suppress("MagicNumber")
private fun provideOkHttp(): OkHttpClient {
    val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    return OkHttpClient.Builder().apply {
        connectTimeout(30L, TimeUnit.SECONDS)
        readTimeout(30L, TimeUnit.SECONDS)
        writeTimeout(60L, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) addInterceptor(logging)
    }.build()
}

private fun provideRetrofit(okHttp: OkHttpClient): Retrofit {
    val moshi = Moshi
        .Builder()
        .addLast(KotlinJsonAdapterFactory())
        .add(DateTimeMoshiAdapter())
        .build()

    return Retrofit.Builder()
        .client(okHttp)
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}

private fun provideGithubApi(retrofit: Retrofit): GithubApi {
    return retrofit.create(GithubApi::class.java)
}