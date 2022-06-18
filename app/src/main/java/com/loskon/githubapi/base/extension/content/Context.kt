package com.loskon.githubapi.base.extension.content

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.material.color.MaterialColors

@ColorInt
fun Context.getColorCtx(@ColorRes color: Int): Int {
    return ContextCompat.getColor(this, color)
}

@ColorInt
fun Context.getThemeMaterialColorKtx(@AttrRes attrRes: Int): Int {
    return MaterialColors.getColor(this, attrRes, 0)
}

@ColorInt
fun Context.getThemeColorKtx(@AttrRes attrRes: Int): Int = TypedValue()
    .apply { theme.resolveAttribute(attrRes, this, true) }
    .data