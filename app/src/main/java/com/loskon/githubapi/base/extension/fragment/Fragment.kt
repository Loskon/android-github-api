package com.loskon.githubapi.base.extension.fragment

import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.fragment.app.Fragment
import com.loskon.githubapi.base.extension.content.getColorControlHighlightKtx
import com.loskon.githubapi.base.extension.content.getColorCtx
import com.loskon.githubapi.base.extension.content.getColorPrimaryKtx
import com.loskon.githubapi.base.extension.content.getFontKtx

val Fragment.getColorPrimary: Int get() = requireContext().getColorPrimaryKtx()

val Fragment.getColorControlHighlight: Int get() = requireContext().getColorControlHighlightKtx()

fun Fragment.getColor(@ColorRes colorId: Int): Int = requireContext().getColorCtx(colorId)

fun Fragment.getFont(@FontRes fontId: Int) = requireContext().getFontKtx(fontId)