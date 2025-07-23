package com.loskon.features.util.imageloader

import android.widget.ImageView
import coil3.load
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import com.loskon.base.R

fun ImageView.loadImage(url: String) {
    load(url) {
        crossfade(true)
        placeholder(R.drawable.ic_placeholder_image_48px)
        transformations(CircleCropTransformation())
        error(R.drawable.ic_placeholder_image_48px)
    }
}