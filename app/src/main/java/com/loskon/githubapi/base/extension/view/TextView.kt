package com.loskon.githubapi.base.extension.view

import android.widget.TextView

fun TextView.textWithGone(txt: String) {
    if (txt.isNotEmpty()) {
        text = txt
        setGoneVisibleKtx(true)
    } else {
        setGoneVisibleKtx(false)
    }
}