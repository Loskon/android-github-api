package com.loskon.base.extension.context

import android.content.Context
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.material.color.MaterialColors

fun Context.getColorKtx(@ColorRes colorId: Int): Int {
    return ContextCompat.getColor(this, colorId)
}

fun Context.getMaterialColorKtx(@AttrRes attrRes: Int): Int {
    return MaterialColors.getColor(this, attrRes, 0)
}

fun Context.getPrimaryColorKtx(): Int {
    return getMaterialColorKtx(android.R.attr.colorPrimary)
}