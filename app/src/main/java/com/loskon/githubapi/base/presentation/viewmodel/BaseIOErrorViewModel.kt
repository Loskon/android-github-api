package com.loskon.githubapi.base.presentation.viewmodel

import com.loskon.githubapi.network.retrofit.exception.EmptyCacheException
import com.loskon.githubapi.network.retrofit.exception.NoSuccessfulException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.net.SocketTimeoutException
import java.net.UnknownHostException

data class IOErrorState(
    val type: IOErrorType? = null,
    val message: String? = null
)

enum class IOErrorType {
    EMPTY_CACHE,
    NO_SUCCESSFUL,
    TIMEOUT,
    UNKNOWN_HOST,
    OTHER
}

open class IOErrorViewModel : BaseViewModel() {

    private val ioErrorAction = MutableStateFlow<IOErrorState?>(null)
    val getIoErrorAction get() = ioErrorAction.asStateFlow()

    fun launchIOErrorJob(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        block: suspend () -> Unit
    ): Job {
        return launchErrorJob(
            dispatcher,
            onErrorBlock = { handleErrors(it) }
        ) {
            block()
        }
    }

    private fun handleErrors(throwable: Throwable?) {
        when (throwable) {
            null -> tryEmitErrorAction(null)
            is EmptyCacheException -> tryEmitErrorAction(IOErrorType.EMPTY_CACHE)
            is NoSuccessfulException -> tryEmitErrorAction(IOErrorType.NO_SUCCESSFUL, throwable.message)
            is UnknownHostException -> tryEmitErrorAction(IOErrorType.TIMEOUT)
            is SocketTimeoutException -> tryEmitErrorAction(IOErrorType.UNKNOWN_HOST)
            else -> tryEmitErrorAction(IOErrorType.OTHER, throwable.message)
        }
    }

    private fun tryEmitErrorAction(error: IOErrorType?, message: String? = null) {
        val value = if (error != null) IOErrorState(error, message) else null
        ioErrorAction.tryEmit(value)
    }

    fun tryEmitIOErrorEmptyCache() {
        tryEmitErrorAction(IOErrorType.EMPTY_CACHE)
    }
}