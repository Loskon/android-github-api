package com.loskon.githubapi.utils

import android.widget.ImageView
import com.loskon.githubapi.app.GlideApp

object ImageLoader {

    fun loadImage(url: String?, view: ImageView) {
        if (url.isNullOrEmpty()) return
        GlideApp.with(view)
            .load(url)
            .circleCrop()
            .into(view)
    }
}