package com.loskon.base.extension.paging

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

inline fun <T : Any, VH : RecyclerView.ViewHolder> PagingDataAdapter<T, VH>.observe(
    lifecycleScope: LifecycleCoroutineScope,
    crossinline block: suspend (LoadState) -> Unit
) {
    lifecycleScope.launch {
        loadStateFlow.collectLatest {
            block(it.refresh)
        }
    }
}