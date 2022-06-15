package com.loskon.githubapi.base.extension.view

import android.widget.TextView

fun TextView.textNotEmpty(text: String) {
    if (text.isNotEmpty()) {
        this.text = text
    } else {
        setGoneVisibleKtx(false)
    }
}