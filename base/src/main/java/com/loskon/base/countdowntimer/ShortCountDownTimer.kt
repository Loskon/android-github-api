package com.loskon.base.countdowntimer

import android.os.CountDownTimer

open class ShortCountDownTimer(
    millis: Long,
    val onFinishTimer: () -> Unit
) : CountDownTimer(millis, millis) {

    override fun onTick(p0: Long) {
        // nothing
    }

    override fun onFinish() = onFinishTimer()
}