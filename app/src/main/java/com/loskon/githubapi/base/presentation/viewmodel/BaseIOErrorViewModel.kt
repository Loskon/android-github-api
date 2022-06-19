package com.loskon.githubapi.base.presentation.viewmodel

import com.loskon.githubapi.network.retrofit.exception.EmptyCacheException
import com.loskon.githubapi.network.retrofit.exception.NoSuccessfulException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val ioErrorState = MutableSharedFlow<IOErrorState?>(replay = 1)
    val getIoErrorState get() = ioErrorState.asSharedFlow()

    fun launchIOErrorJob(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        errorFunction: ((Throwable) -> Unit)? = null,
        function: suspend () -> Unit
    ): Job {
        return launchErrorJob(
            dispatcher,
            errorFunction = { throwable -> handleErrorFunction(throwable, errorFunction) }
        ) {
            function()
        }
    }

    private fun handleErrorFunction(
        throwable: Throwable,
        errorFunction: ((Throwable) -> Unit)? = null
    ) {
        errorFunction?.invoke(throwable)
        handleErrors(throwable)
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
        ioErrorState.tryEmit(value)
    }
}