package com.loskon.githubapi.network.retrofit.data

import android.content.Context
import com.loskon.githubapi.network.retrofit.CACHE_DIR_NAME
import com.loskon.githubapi.network.retrofit.data.exception.EmptyCacheException
import com.loskon.githubapi.utils.NetworkUtil
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.util.concurrent.TimeUnit

class CacheInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        return if (NetworkUtil.hasConnected(context)) {
            val onlineRequest = request.onlineCacheControl()
            chain.proceed(onlineRequest).addCacheHeader(false)
        } else {
            if (hasDataCacheDir()) {
                val offlineRequest = request.offlineCacheControl()
                chain.proceed(offlineRequest).addCacheHeader(true)
            } else {
                throw EmptyCacheException()
            }
        }
    }

    private fun hasDataCacheDir(): Boolean {
        val path = context.cacheDir.path + File.separator + CACHE_DIR_NAME
        return File(path).exists()
    }

    private fun Request.onlineCacheControl(): Request {
        val cacheControl = CacheControl.Builder().maxAge(1, TimeUnit.MINUTES).build()
        return newBuilder().header(CACHE_CONTROL, cacheControl.toString()).build()
    }

    private fun Request.offlineCacheControl(): Request {
        val cacheControl = CacheControl.Builder().maxStale(1, TimeUnit.DAYS).build()
        return newBuilder().cacheControl(cacheControl).build()
    }

    private fun Response.addCacheHeader(fromCache: Boolean): Response {
        return newBuilder().addHeader(CACHE_HEADER, fromCache.toString()).build()
    }

    companion object {
        const val CACHE_HEADER = "Cache-Header"
        private const val CACHE_CONTROL = "Cache-Control"
    }
}