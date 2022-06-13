package com.loskon.githubapi.base.extension.view

import android.os.SystemClock
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager

fun View.setDebounceClickListener(debounceTime: Long = 600L, action: () -> Unit) {
    setOnClickListener(object : View.OnClickListener {

        private var lastClickTime: Long = 0

        override fun onClick(view: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) {
                return
            } else {
                action()
            }

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

fun View.setVisibleKtx(visible: Boolean) {
    visibility = if (isVisible == visible) {
        return
    } else {
        if (visible) View.VISIBLE else View.INVISIBLE
    }
}

fun View.setGoneVisibleKtx(visible: Boolean) {
    visibility = if (isVisible == visible) {
        return
    } else {
        if (visible) View.VISIBLE else View.GONE
    }
}

fun View.toggle(parent: ViewGroup, show: Boolean) {
    val transition: Transition = Slide(Gravity.TOP)
    transition.duration = 600
    transition.addTarget(this)
    TransitionManager.beginDelayedTransition(parent, transition)
    visibility = if (show) View.VISIBLE else View.GONE
}