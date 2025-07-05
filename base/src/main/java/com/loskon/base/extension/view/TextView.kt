package com.loskon.base.extension.view

import android.widget.TextView

fun TextView.textWithGone(text: String) {
    if (text.isNotEmpty()) {
        this.text = text
        setGoneVisibleKtx(true)
    } else {
        setGoneVisibleKtx(false)
    }
}

fun TextView.setTextClickListener(
    block: (text: String) -> Unit
) {
    setDebounceClickListener { block(text.toString()) }
}