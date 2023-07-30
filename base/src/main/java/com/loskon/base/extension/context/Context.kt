package com.loskon.base.extension.context

import android.content.Context
import android.graphics.Typeface
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.color.MaterialColors

fun Context.getColorKtx(@ColorRes colorId: Int): Int {
    return ContextCompat.getColor(this, colorId)
}

fun Context.getFontKtx(@FontRes fontId: Int): Typeface? {
    return ResourcesCompat.getFont(this, fontId)
}

fun Context.getDimenKtx(@DimenRes dimenId: Int): Int {
    return resources.getDimension(dimenId).toInt()
}

fun Context.getMaterialColorKtx(@AttrRes attrRes: Int): Int {
    return MaterialColors.getColor(this, attrRes, 0)
}

fun Context.getControlHighlightColorKtx(): Int {
    return getMaterialColorKtx(android.R.attr.colorControlHighlight)
}

fun Context.getPrimaryColorKtx(): Int {
    return getMaterialColorKtx(android.R.attr.colorPrimary)
}