package com.loskon.githubapi.app.base.presentation.viewmodel

import com.loskon.githubapi.domain.exception.EmptyCacheException
import com.loskon.githubapi.domain.exception.NoSuccessfulException
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
        errorBlock: ((Throwable) -> Unit)? = null,
        block: suspend () -> Unit
    ): Job {
        return launchErrorJob(
            dispatcher,
            errorBlock = { throwable -> handleErrorFunction(throwable, errorBlock) }
        ) {
            block()
        }
    }

    private fun handleErrorFunction(
        throwable: Throwable,
        errorBlock: ((Throwable) -> Unit)? = null
    ) {
        errorBlock?.invoke(throwable)
        handleErrors(throwable)
    }

    private fun handleErrors(throwable: Throwable?) {
        when (throwable) {
            null -> tryEmitErrorState(null)
            is EmptyCacheException -> tryEmitErrorState(IOErrorType.EMPTY_CACHE)
            is NoSuccessfulException -> tryEmitErrorState(IOErrorType.NO_SUCCESSFUL, throwable.message)
            is UnknownHostException -> tryEmitErrorState(IOErrorType.TIMEOUT)
            is SocketTimeoutException -> tryEmitErrorState(IOErrorType.UNKNOWN_HOST)
            else -> tryEmitErrorState(IOErrorType.OTHER, throwable.message)
        }
    }

    private fun tryEmitErrorState(error: IOErrorType?, message: String? = null) {
        val errorStateValue = getErrorStateValue(error, message)
        ioErrorState.tryEmit(errorStateValue)
    }

    private fun getErrorStateValue(error: IOErrorType?, message: String?): IOErrorState? {
        return if (error != null) {
            IOErrorState(error, message)
        } else {
            null
        }
    }
}