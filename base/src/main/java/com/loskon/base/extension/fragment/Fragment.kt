package com.loskon.base.extension.fragment

import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.FontRes
import androidx.fragment.app.Fragment
import com.loskon.base.extension.context.getColorKtx
import com.loskon.base.extension.context.getControlHighlightColorKtx
import com.loskon.base.extension.context.getDimenKtx
import com.loskon.base.extension.context.getFontKtx
import com.loskon.base.extension.context.getPrimaryColorKtx

val Fragment.primaryColor: Int get() = requireContext().getPrimaryColorKtx()

val Fragment.controlHighlightColor: Int get() = requireContext().getControlHighlightColorKtx()

fun Fragment.getColor(@ColorRes colorId: Int): Int = requireContext().getColorKtx(colorId)

fun Fragment.getFont(@FontRes fontId: Int) = requireContext().getFontKtx(fontId)

fun Fragment.getDimen(@DimenRes dimenId: Int): Int = requireContext().getDimenKtx(dimenId)

inline fun <T : Fragment> T.putArgs(argsBuilder: Bundle.() -> Unit): T = apply {
    arguments = Bundle().apply(argsBuilder)
}