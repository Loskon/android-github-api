package com.loskon.base.extension.fragment

import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import com.loskon.base.extension.context.getColorKtx
import com.loskon.base.extension.context.getPrimaryColorKtx

val Fragment.primaryColor: Int get() = requireContext().getPrimaryColorKtx()

fun Fragment.getColor(@ColorRes colorId: Int): Int = requireContext().getColorKtx(colorId)