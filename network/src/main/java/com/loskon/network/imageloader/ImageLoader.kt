package com.loskon.network.imageloader

import android.widget.ImageView
import coil.load
import coil.transform.CircleCropTransformation
import com.loskon.network.R

object ImageLoader {
    fun load(view: ImageView, url: String) {
        view.load(url) {
            crossfade(true)
            error(R.drawable.ic_placeholder_image_48px)
            placeholder(R.drawable.ic_placeholder_image_48px)
            transformations(CircleCropTransformation())
        }
    }
}