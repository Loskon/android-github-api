package com.loskon.base.extension.view

import android.util.TypedValue
import android.widget.TextView

fun TextView.textWithGone(text: String) {
    if (text.isNotEmpty()) {
        this.text = text
        setGoneVisibleKtx(true)
    } else {
        setGoneVisibleKtx(false)
    }
}

fun TextView.setClickListener(action: (text: String) -> Unit) = setDebounceClickListener { action(text.toString()) }

fun TextView.setTextSizeKtx(fontSize: Float) = setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)