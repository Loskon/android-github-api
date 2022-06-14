package com.loskon.githubapi.network.glide

import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageLoader {

    fun loadImage(url: String?, view: ImageView) {
        if (url.isNullOrEmpty()) return
        Glide.with(view)
            .load(url)
            .circleCrop()
            .into(view)
    }
}