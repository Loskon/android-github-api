package com.loskon.githubapi.utils

import android.widget.ImageView
import coil.load
import coil.transform.CircleCropTransformation

object ImageLoader {

    fun loadImage(view: ImageView, url: String) {
        view.load(url) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
    }
}