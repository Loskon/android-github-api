package com.loskon.base.extension.coroutines

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Restartable Lifecycle-aware coroutines.
 * repeatOnLifecycle restarts its coroutine
 * from scratch on each repeat, and cancels it each
 * time lifecycle falls below the specified state
 */
fun <T> Flow<T>.observe(
    lifecycle: LifecycleOwner,
    block: suspend (T) -> Unit
) {
    lifecycle.lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collect(block)
        }
    }
}