package com.loskon.githubapi.app.base.extension.view

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

fun SwipeRefreshLayout.setRefreshingKtx(refresh: Boolean) {
    isRefreshing = if (isRefreshing == refresh) {
        return
    } else {
        refresh
    }
}