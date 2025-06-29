package com.loskon.base.extension.view

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

fun SwipeRefreshLayout.setOnCanceledRefreshListener(action: () -> Unit) {
    setOnRefreshListener {
        isRefreshing = false
        action()
    }
}