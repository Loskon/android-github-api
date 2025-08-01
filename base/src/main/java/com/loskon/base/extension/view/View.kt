package com.loskon.base.extension.view

import android.os.SystemClock
import android.view.View
import androidx.core.view.isVisible

fun View.setDebounceClickListener(debounceTime: Long = 600L, action: () -> Unit) {
    setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(view: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) {
                return
            } else {
                action()
            }

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

fun View.setGoneVisibleKtx(visible: Boolean) {
    visibility = if (isVisible == visible) {
        return
    } else {
        if (visible) View.VISIBLE else View.GONE
    }
}