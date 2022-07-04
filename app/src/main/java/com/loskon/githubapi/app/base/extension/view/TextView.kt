package com.loskon.githubapi.app.base.extension.view

import android.widget.TextView

fun TextView.textWithGone(text: String) {
    if (text.isNotEmpty()) {
        this.text = text
        setGoneVisibleKtx(true)
    } else {
        setGoneVisibleKtx(false)
    }
}