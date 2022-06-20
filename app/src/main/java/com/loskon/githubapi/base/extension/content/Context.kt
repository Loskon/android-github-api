package com.loskon.githubapi.base.extension.content

import android.content.Context
import android.graphics.Typeface
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.color.MaterialColors

fun Context.getColorCtx(@ColorRes colorId: Int): Int {
    return ContextCompat.getColor(this, colorId)
}

fun Context.getFontKtx(@FontRes fontId: Int): Typeface? {
    return ResourcesCompat.getFont(this, fontId)
}

fun Context.getThemeMaterialColorKtx(@AttrRes attrRes: Int): Int {
    return MaterialColors.getColor(this, attrRes, 0)
}

fun Context.getThemeColorKtx(@AttrRes attrRes: Int): Int = TypedValue()
    .apply { theme.resolveAttribute(attrRes, this, true) }
    .data

fun Context.getColorControlHighlightKtx(): Int {
    return getThemeMaterialColorKtx(android.R.attr.colorControlHighlight)
}

fun Context.getColorPrimaryKtx(): Int {
    return getThemeMaterialColorKtx(android.R.attr.colorPrimary)
}

fun Context.getColorAccentKtx(): Int {
    return getThemeMaterialColorKtx(android.R.attr.colorAccent)
}