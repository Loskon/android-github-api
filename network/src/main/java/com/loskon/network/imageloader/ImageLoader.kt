package com.loskon.network.imageloader

import android.widget.ImageView
import coil.load
import coil.transform.CircleCropTransformation

object ImageLoader {
    fun load(view: ImageView, url: String) {
        view.load(url) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
    }
}