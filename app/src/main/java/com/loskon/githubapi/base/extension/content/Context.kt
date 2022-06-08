package com.loskon.template.base.extension.content

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.getColorCtx(@ColorRes color: Int): Int {
    return ContextCompat.getColor(this, color)
}