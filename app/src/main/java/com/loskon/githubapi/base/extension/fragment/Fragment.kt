package com.loskon.githubapi.base.extension.fragment

import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.fragment.app.Fragment
import com.loskon.githubapi.base.extension.context.getColorControlHighlightKtx
import com.loskon.githubapi.base.extension.context.getColorCtx
import com.loskon.githubapi.base.extension.context.getColorPrimaryKtx
import com.loskon.githubapi.base.extension.context.getFontKtx

val Fragment.getColorPrimary: Int get() = requireContext().getColorPrimaryKtx()

val Fragment.getColorControlHighlight: Int get() = requireContext().getColorControlHighlightKtx()

fun Fragment.getColor(@ColorRes colorId: Int): Int = requireContext().getColorCtx(colorId)

fun Fragment.getFont(@FontRes fontId: Int) = requireContext().getFontKtx(fontId)