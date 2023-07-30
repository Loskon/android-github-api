package com.loskon.base.extension.integer

import android.content.res.Resources
import kotlin.math.roundToInt

val Int.dp: Int get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()

val Int.sp get() = this * Resources.getSystem().displayMetrics.scaledDensity