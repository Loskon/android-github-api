package com.loskon.githubapi.base.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    private val errorState = MutableStateFlow<Throwable?>(null)
    open val errorStateFlow get() = errorState.asStateFlow()

    protected fun launchErrorJob(
        error: MutableStateFlow<Throwable?>? = errorState,
        onErrorBlock: ((Throwable) -> Unit)? = null,
        block: suspend () -> Unit
    ): Job {
        return viewModelScope.launch() {
            try {
                error?.tryEmit(null)
                block()
            } finally {
                // Nothing
            }
        }
    }

    private fun getExceptionHandler(
        error: MutableStateFlow<Throwable?>?,
        onErrorBlock: ((Throwable) -> Unit)? = null
    ): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            onErrorBlock?.invoke(throwable)
            error?.tryEmit(throwable)
        }
    }
}