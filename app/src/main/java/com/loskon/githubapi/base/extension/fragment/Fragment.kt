package com.loskon.githubapi.base.extension.fragment

import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import com.loskon.githubapi.base.extension.content.getColorPrimaryKtx

@ColorInt
fun Fragment.getColorPrimary(): Int = requireContext().getColorPrimaryKtx()
